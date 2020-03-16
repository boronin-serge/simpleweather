package ru.boronin.common.view.camera

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.ImageReader
import android.util.AttributeSet
import android.util.Size
import android.view.Surface
import androidx.annotation.RequiresPermission
import androidx.core.content.getSystemService
import androidx.core.content.withStyledAttributes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.tbruyelle.rxpermissions2.RxPermissions
import ru.boronin.common.view.R
import java.io.File
import java.util.*
import java.util.concurrent.Executors
import kotlin.math.max

private const val PATH_SAVE_IMAGES = "/photo"

class CameraView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AutoFitTextureView(context, attrs, defStyleAttr), LifecycleObserver {
  private var cameraManager: CameraManager? = null
  private var previewSize: Size? = null
  private var cameraDevice: CameraDevice? = null
  private var imageReader: ImageReader? = null
  private var cameraCaptureSession: CameraCaptureSession? = null
  private var previewBuilder: CaptureRequest.Builder? = null
  private var captureBuilder: CaptureRequest.Builder? = null

  private var frontCameraId = ""
  private var backCameraId = ""

  private var callbackSaveImage: ((String) -> Unit)? = null
  private var desiredPhotoSize: Int? = null
  private var imageDir: String? = null

  private var currentCamera: Camera =
    Camera.CAMERA_BACK
  private var isStartCameraPreview = false
  var cameraAvailableCallback: CameraAvailableCallback? = null

  private val executor = Executors.newSingleThreadExecutor()

  private val Camera.cameraId
    get() = when (this) {
      Camera.CAMERA_BACK -> backCameraId
      Camera.CAMERA_FRONT -> frontCameraId
    }

  private val surfaceTextureListener = object : SurfaceTextureListener {
    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) { }
    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) { }
    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean = false

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
      checkPermissionsAndOpenCamera(width, height)
    }
  }

  private val deviceStateCallback = object : CameraDevice.StateCallback() {
    override fun onOpened(camera: CameraDevice) {
      cameraDevice = camera
      startPreview()
    }

    override fun onDisconnected(camera: CameraDevice) {
      releaseCamera()
    }

    override fun onError(camera: CameraDevice, error: Int) {
      onDisconnected(camera)
    }
  }

  private val cameraPreviewSessionCallback = object : CameraCaptureSession.StateCallback() {
    override fun onConfigureFailed(session: CameraCaptureSession) { }

    override fun onConfigured(session: CameraCaptureSession) {
      cameraCaptureSession = session
      try {
        previewBuilder?.build()
          ?.also { session.setRepeatingRequest(
            it,
            object : CameraCaptureSession.CaptureCallback() {
              override fun onCaptureStarted(
                session: CameraCaptureSession,
                request: CaptureRequest,
                timestamp: Long,
                frameNumber: Long
              ) {
                super.onCaptureStarted(session, request, timestamp, frameNumber)

                if (!isStartCameraPreview) {
                  cameraAvailableCallback?.cameraAvailableCallback(true)
                  isStartCameraPreview = true
                }
              }
            },
            null
          )
          }
      } catch (e: CameraAccessException) {
        e.printStackTrace()
      }
    }
  }

  private val cameraCaptureSessionCallback = object : CameraCaptureSession.StateCallback() {
    override fun onConfigureFailed(session: CameraCaptureSession) { }

    override fun onConfigured(session: CameraCaptureSession) {
      try {
        captureBuilder?.build()
          ?.also { request -> session.capture(request, null, null) }
      } catch (e: CameraAccessException) {
        e.printStackTrace()
      }
    }
  }

  init {
    context.withStyledAttributes(attrs, R.styleable.CameraView) {
      val currentCameraInt = getInt(R.styleable.CameraView_camera, Camera.CAMERA_BACK.ordinal)
      currentCamera = Camera.values()[currentCameraInt]
    }

    cameraManager = context.getSystemService<CameraManager>()
    val cameras = getCameraIds()
    frontCameraId = cameras.first
    backCameraId = cameras.second
  }


  // region Api

  fun registerLifecycle(lifecycle: Lifecycle) {
    lifecycle.addObserver(this)
  }

  fun changeCamera(camera: Camera) {
    currentCamera = camera
    releaseCamera()
    checkPermissionsAndOpenCamera()
  }

  fun takePhoto(callbackSaveImage: ((String) -> Unit)? = null) {
    this.callbackSaveImage = callbackSaveImage

    val aeFlash = previewBuilder?.get(CaptureRequest.CONTROL_AE_MODE)
    val afMode = previewBuilder?.get(CaptureRequest.CONTROL_AF_MODE)
    val flashMode = previewBuilder?.get(CaptureRequest.FLASH_MODE)

    val characteristics = cameraManager?.getCameraCharacteristics(currentCamera.cameraId)
    val orientation = characteristics?.get(CameraCharacteristics.SENSOR_ORIENTATION)

    captureBuilder = createCaptureRequestBuilder(
      CameraDevice.TEMPLATE_STILL_CAPTURE,
      imageReader?.surface
    )?.apply {
      set(CaptureRequest.CONTROL_AE_MODE, aeFlash)
      set(CaptureRequest.CONTROL_AF_MODE, afMode)
      set(CaptureRequest.FLASH_MODE, flashMode)
      set(CaptureRequest.JPEG_ORIENTATION, orientation)
    }

    cameraDevice?.createCaptureSession(
      listOf(imageReader?.surface),
      cameraCaptureSessionCallback,
      null
    )
  }

  fun startPreview() {
    try {
      createCameraPreviewSession()
    } catch (e: CameraAccessException) {
      e.printStackTrace()
    }
  }

  fun deletePhoto(path: String) {
    executor.execute {
      try {
        val file = File(path)
        if (file.exists()) {
          file.delete()
        }
      } catch (e: CameraAccessException) {
        e.printStackTrace()
      }
    }
  }

  fun deleteAllPhotos() {
    executor.execute {
      try {
        context.getExternalFilesDir(PATH_SAVE_IMAGES + imageDir.orEmpty()).also { photoDir ->
          photoDir?.listFiles()?.forEach {
            if (it.exists()) {
              it.delete()
            }
          }
        }
      } catch (e: CameraAccessException) {
        e.printStackTrace()
      }
    }
  }

  /**
   * Camera has a few default dimensions of the surface.
   * The variable desiredPhotoSize will be used as
   * the nearest value among default sizes.
   */
  fun setDesiredPhotoSize(desiredPhotoSize: Int) {
    this.desiredPhotoSize = desiredPhotoSize
  }

  /**
   * @name - path name after @PATH_SAVE_IMAGES
   * e.g. @PATH_SAVE_IMAGES/{name}
   */
  fun setDirectoryName(name: String) {
    imageDir = name
  }

  // endregion


  // region Private

  private fun getCameraIds(): Pair<String, String> {
    var frontCameraId = ""
    var backCameraId = ""

    cameraManager?.cameraIdList
      ?.forEach {
        val cameraCharacteristics = cameraManager?.getCameraCharacteristics(it)

        when (cameraCharacteristics?.get(CameraCharacteristics.LENS_FACING)) {
          CameraCharacteristics.LENS_FACING_FRONT -> frontCameraId = it
          CameraCharacteristics.LENS_FACING_BACK -> backCameraId = it
        }
      }

    return Pair(frontCameraId, backCameraId)
  }

  @SuppressLint("MissingPermission")
  private fun checkPermissionsAndOpenCamera(
    widthView: Int = measuredWidth,
    heightView: Int = measuredHeight
  ) {
    cameraAvailableCallback?.cameraAvailableCallback(false)
    isStartCameraPreview = false
    post {
      val width = if (widthView == 0) measuredWidth else widthView
      val height = if (heightView == 0) measuredHeight else heightView
      (context as? Activity)?.also { activity ->
        RxPermissions(activity).requestEach(android.Manifest.permission.CAMERA)
          .subscribe {
            if (it.name == android.Manifest.permission.CAMERA && it.granted) {
              openCamera(width, height)
            }
          }
      }
    }
  }

  @RequiresPermission(android.Manifest.permission.CAMERA)
  private fun openCamera(widthView: Int = measuredWidth, heightView: Int = measuredHeight) {
    isStartCameraPreview
    if (isAvailable) {
      if (desiredPhotoSize == null) {
        setUpCameraOutputs(widthView, heightView)
      } else {
        val desiredSize = convertToDesiredSize(widthView, heightView)
        setUpCameraOutputs(desiredSize.width, desiredSize.height)
      }

      try {
        cameraManager?.openCamera(currentCamera.cameraId, deviceStateCallback, null)
      } catch (e: CameraAccessException) {
        e.printStackTrace()
      }
    } else {
      setSurfaceTextureListener(surfaceTextureListener)
    }
  }

  private fun setUpCameraOutputs(width: Int, height: Int) {
    try {
      val characteristics = cameraManager?.getCameraCharacteristics(currentCamera.cameraId)
      val map = characteristics?.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)

      map?.also {
        val largest = Collections.max(
          listOf(*map.getOutputSizes(ImageFormat.JPEG)),
          CompareSizesByArea()
        )

        previewSize = chooseOptimalSize(
          map.getOutputSizes(SurfaceTexture::class.java),
          width,
          height,
          largest,
          hasTopLimit = false
        )

        if (imageReader != null) {
          imageReader?.close()
          imageReader = null
        }

        imageReader = ImageReader.newInstance(
          previewSize?.width ?: 0,
          previewSize?.height ?: 0,
          ImageFormat.JPEG,
          1
        )

        context.getExternalFilesDir(PATH_SAVE_IMAGES + imageDir.orEmpty())
          ?.also {
            imageReader?.setOnImageAvailableListener(
              JpegImageAvailable(it) { pathImage ->
                callbackSaveImage?.invoke(
                  pathImage
                )
              },
              null
            )
          }

        val orientation = resources?.configuration?.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
          setAspectRatio(previewSize?.width ?: 0, previewSize?.height ?: 0)
        } else {
          setAspectRatio(previewSize?.height ?: 0, previewSize?.width ?: 0)
        }
      }
    } catch (e: CameraAccessException) {
      e.printStackTrace()
    }
  }

  private fun chooseOptimalSize(
    choices: Array<Size>,
    width: Int,
    height: Int,
    aspectRatio: Size,
    hasTopLimit: Boolean = false
  ): Size {
    val suitableSizes = ArrayList<Size>()
    val w = aspectRatio.width
    val h = aspectRatio.height
    for (option in choices) {
      val ratioSame = option.height == option.width * h / w
      val widthFit = if (hasTopLimit) option.width <= width else option.width >= width
      val heightFit = if (hasTopLimit) option.height <= height else option.height >= height

      if (ratioSame && widthFit && heightFit) {
        suitableSizes.add(option)
      }
    }

    return if (suitableSizes.size > 0) {
      if (hasTopLimit) {
        Collections.max(suitableSizes,
          CompareSizesByArea()
        )
      } else {
        Collections.min(suitableSizes,
          CompareSizesByArea()
        )
      }
    } else {
      choices[0]
    }
  }

  private fun createCameraPreviewSession() {
    surfaceTexture?.setDefaultBufferSize(
      previewSize?.width ?: 0,
      previewSize?.height ?: 0
    )

    val surface = Surface(surfaceTexture)

    previewBuilder = createCaptureRequestBuilder(
      templateType = CameraDevice.TEMPLATE_PREVIEW,
      surface = surface
    )

    cameraDevice?.createCaptureSession(
      Collections.singletonList(surface),
      cameraPreviewSessionCallback,
      null
    )
  }

  private fun convertToDesiredSize(width: Int, height: Int): Size {
    val maxSize = max(width, height).toFloat()
    val q = desiredPhotoSize?.div(maxSize) ?: 1f
    val desiredWidth = (width * q).toInt()
    val desiredHeight = (height * q).toInt()
    return Size(desiredWidth, desiredHeight)
  }

  private fun releaseCamera() {
    imageReader?.close()
    imageReader = null

    cameraDevice?.close()
    cameraDevice = null

    cameraCaptureSession?.close()
    cameraCaptureSession = null

    previewBuilder = null
    captureBuilder = null
    callbackSaveImage = null
  }

  private fun createCaptureRequestBuilder(
    templateType: Int,
    surface: Surface?
  ) = cameraDevice?.createCaptureRequest(templateType)?.apply { surface?.also { addTarget(it) } }

  // endregion


  // region Lifecycle

  @SuppressLint("MissingPermission")
  @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
  fun onResume() {
    checkPermissionsAndOpenCamera()
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
  fun onPause() {
    releaseCamera()
  }

  // endregion


  enum class Camera {
    CAMERA_BACK,
    CAMERA_FRONT;
  }

  interface CameraAvailableCallback {
    fun cameraAvailableCallback(available: Boolean)
  }
}