package ru.boronin.common.navigation

import android.content.Intent
import androidx.fragment.app.Fragment

interface ScreenResultProvider {
  var result: ScreenResult?

  fun setResult(resultCode: Int, data: Intent? = null)
}