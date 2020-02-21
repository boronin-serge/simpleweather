package ru.boronin.common.navigation

import android.content.Intent

data class ScreenResult(
  val requestCode: Int?,
  val resultCode: Int = 0,
  val data: Intent? = null
)