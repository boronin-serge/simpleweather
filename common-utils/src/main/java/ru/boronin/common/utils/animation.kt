package ru.boronin.common.utils

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import androidx.annotation.ColorInt

fun animationChangeColor(
  @ColorInt startColor: Int,
  @ColorInt endColor: Int,
  animationFun: (ValueAnimator) -> Unit
) {
  with(ValueAnimator.ofObject(ArgbEvaluator(), startColor, endColor)) {
    duration = DEFAULT_ANIMATION_DURATION
    addUpdateListener { animationFun.invoke(it) }
    start()
  }
}