package ru.boronin.core.api.resource

import android.content.res.ColorStateList
import androidx.annotation.*

interface ResourcesProvider {
  fun getString(@StringRes resId: Int): String
  fun getString(@StringRes resId: Int, vararg formatArgs: Any): String
  fun getStringArray(@ArrayRes resId: Int): Array<String>
  fun getQuantityString(@PluralsRes pluralId: Int, quantity: Int, vararg formatArgs: Any): String
  fun getColor(@ColorRes colorRes: Int): Int
  fun getColorStateList(@ColorRes id: Int): ColorStateList
  fun getDrawableRes(fileName: String): Int
  fun getDimensionPixelSize(@DimenRes id: Int): Int
  fun getInteger(@IntegerRes res: Int): Int
}