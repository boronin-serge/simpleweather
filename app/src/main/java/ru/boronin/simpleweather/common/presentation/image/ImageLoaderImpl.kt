package ru.boronin.simpleweather.common.presentation.image

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

const val TAG = "Glide"

class ImageLoaderImpl(
  private val requestManager: RequestManager
) : ImageLoader {
  override fun loadImage(id: String, imageView: ImageView) {
    requestManager.load("https://openweathermap.org/img/wn/$id@2x.png")
      .listener(
        object : RequestListener<Drawable> {
          override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
          ): Boolean {
            Log.e(TAG, "Load failed", e)

            // You can also log the individual causes:
            for (t in e!!.rootCauses) {
              Log.e(TAG, "Caused by", t)
            }
            // Or, to log all root causes locally, you can use the built in helper method:
            e.logRootCauses(TAG)

            return false // Allow calling onLoadFailed on the Target.
          }

          override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
          ): Boolean {
            Log.d("Glide", "everything is ok")
            return false
          }
        }
      )
      .into(imageView)
  }
}
