package ru.boronin.simpleweather.common.presentation.image

import android.widget.ImageView

interface ImageLoader {
  fun loadImage(id: String, imageView: ImageView)
}