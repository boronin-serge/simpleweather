package ru.boronin.common.extension.widget

import android.content.res.ColorStateList
import android.widget.TextView
import androidx.annotation.ColorInt
import ru.boronin.common.utils.animationChangeColor

fun TextView.animationChangeColor(color: ColorStateList) {
  animationChangeColor(color.defaultColor)
}

fun TextView.animationChangeColor(@ColorInt color: Int) {
  animationChangeColor(textColors.defaultColor, color) { valueAnimator ->
    (valueAnimator.animatedValue as? Int)?.also { setTextColor(it) }
  }
}