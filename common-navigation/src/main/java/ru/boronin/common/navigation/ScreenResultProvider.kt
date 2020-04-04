package ru.boronin.common.navigation

import android.content.Intent

interface ScreenResultProvider {
  var result: ScreenResult?

  fun setResult(resultCode: Int, data: Intent? = null)
}