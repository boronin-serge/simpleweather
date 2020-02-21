package ru.boronin.common.extension.core

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.view.Window
import androidx.annotation.ColorInt

fun Window.setStatusBarColor(@ColorInt color: Int, lightStatusBar: Boolean = false) {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    statusBarColor = color
  } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    var flags = decorView.systemUiVisibility

    flags = if (lightStatusBar) {
      flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
      0
    }

    decorView.systemUiVisibility = flags
    setMiuiLightStatusBar(lightStatusBar)
  }
}

@SuppressLint("PrivateApi")
private fun Window.setMiuiLightStatusBar(lightStatusBar: Boolean): Boolean {
  try {
    val darkModeFlag: Int
    val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
    val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
    darkModeFlag = field.getInt(layoutParams)
    val extraFlagField = javaClass.getMethod(
      "setExtraFlags",
      Int::class.javaPrimitiveType,
      Int::class.javaPrimitiveType
    )

    extraFlagField.invoke(
      this,
      if (lightStatusBar) darkModeFlag else 0,
      darkModeFlag
    )

    return true
  } catch (t: Throwable) {
    t.printStackTrace()
  }

  return false
}