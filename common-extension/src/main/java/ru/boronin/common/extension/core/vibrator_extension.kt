package ru.boronin.common.extension.core

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresPermission

private const val DEFAULT_VIBRATE_DURATION = 50L

@RequiresPermission(android.Manifest.permission.VIBRATE)
fun Vibrator.vibrateCompat(duration: Long = DEFAULT_VIBRATE_DURATION) {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
  } else {
    vibrate(duration)
  }
}