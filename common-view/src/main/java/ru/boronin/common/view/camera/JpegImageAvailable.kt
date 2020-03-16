package ru.boronin.common.view.camera

import android.media.Image
import android.media.ImageReader
import android.os.Handler
import android.os.Looper
import java.io.*
import java.lang.Exception
import java.util.concurrent.Executors

class JpegImageAvailable(
  private val folder: File,
  private val callback: ((String) -> Unit)? = null
) : ImageReader.OnImageAvailableListener {
  private val executor = Executors.newSingleThreadExecutor()
  private val uiHandler = Handler(Looper.getMainLooper())

  override fun onImageAvailable(reader: ImageReader?) {
    executor.execute {
      try {
        val image = reader?.acquireLatestImage()
        image?.use { useImage ->
          val bytes = useImage.getBytes()

          try {
            var outputStream: OutputStream? = null
            try {
              val file = getImageFile()
              outputStream = FileOutputStream(file)
              bytes?.also { outputStream.write(it) }
              uiHandler.post { callback?.invoke(file.absolutePath) }
            } catch (e: FileNotFoundException) {
              e.printStackTrace()
            } catch (e: IOException) {
              e.printStackTrace()
            } finally {
              outputStream?.close()
            }
          } catch (e: IOException) {
            e.printStackTrace()
          }
        }
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  private fun Image?.getBytes() = this?.planes
    ?.firstOrNull()
    ?.buffer
    ?.let { ByteArray(it.remaining()).apply { it.get(this) } }

  private fun getImageFile() = File(folder, generationNameImage())
  private fun generationNameImage() = "${System.currentTimeMillis()}_photo.jpeg"
}