package ru.boronin.common.extension.paint.drawable

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

private const val DRAWABLE_TYPE = "drawable"

fun Drawable.tint(context: Context?, @ColorRes color: Int) {
  context?.let { tint(ContextCompat.getColor(context, color)) }
}

fun Drawable.tint(@ColorInt color: Int) {
  val wrapDrawable = DrawableCompat.wrap(this)
  if (color != 0 && wrapDrawable != null) {
    DrawableCompat.setTint(wrapDrawable, color)
    wrapDrawable.mutate()
  }
}

fun Context.getDrawableRes(
  fileName: String
) = resources?.getIdentifier(fileName, DRAWABLE_TYPE, packageName)