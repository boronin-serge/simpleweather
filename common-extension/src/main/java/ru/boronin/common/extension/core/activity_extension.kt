package ru.boronin.common.extension.core

import android.app.Activity
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorInt

fun Activity.hideKeyboard() {
  window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
  hideKeyboard(currentFocus)
}

fun Activity.setStatusBarColor(
  @ColorInt color: Int,
  lightStatusBar: Boolean = false
) = window.setStatusBarColor(color, lightStatusBar)

fun Activity.setTransparentStatusBar(transparent: Boolean) {
  val visible = if (transparent) {
    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
  } else {
    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
  }

  window
    ?.decorView
    ?.systemUiVisibility = visible
}