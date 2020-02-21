package ru.boronin.common.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.Display
import android.view.WindowManager
import androidx.core.content.getSystemService
import java.lang.reflect.InvocationTargetException

fun Context.getNavigationBarSize(): Point {
  val appUsableSize = getAppUsableScreenSize()
  val realScreenSize = getRealScreenSize()
  return Point(realScreenSize.x - appUsableSize.x, realScreenSize.y - appUsableSize.y)
}

private fun Context.getAppUsableScreenSize(): Point {
  val windowManager = getSystemService<WindowManager>()
  val display = windowManager?.defaultDisplay
  val size = Point()
  display?.getSize(size)
  return size
}

@SuppressLint("ObsoleteSdkInt")
private fun Context.getRealScreenSize(): Point {
  val windowManager = getSystemService<WindowManager>()
  val display = windowManager?.defaultDisplay
  val size = Point()

  if (Build.VERSION.SDK_INT >= 17) {
    display?.getRealSize(size)
  } else if (Build.VERSION.SDK_INT >= 14) {
    try {
      val rawWidth = Display::class.java.getMethod("getRawWidth")
      val rawHeight = Display::class.java.getMethod("getRawHeight")
      size.x = rawWidth.invoke(display) as Int
      size.y = rawHeight.invoke(display) as Int
    }
    catch (e: IllegalAccessException) { }
    catch (e: InvocationTargetException) { }
    catch (e: NoSuchMethodException) { }
  }
  return size
}