package ru.boronin.common.extension.core

import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat

val Context?.locale
  get() = this?.resources
    ?.configuration
    ?.let {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        it.locales[0]
      } else {
        it.locale
      }
    }

fun Context.hideKeyboard(view: View?) {
  try {
    if (view != null && view.windowToken != null) {
      val inputMethodService = getSystemService(Context.INPUT_METHOD_SERVICE)
      val inputMethod = inputMethodService as? InputMethodManager
      inputMethod?.hideSoftInputFromWindow(view.windowToken, 0)
    }
  } catch (t: Throwable) {
    t.printStackTrace()
  }
}

fun Context?.findDrawable(
  @DrawableRes res: Int
) = this?.let { AppCompatResources.getDrawable(it, res) }

fun Context?.findColor(@ColorRes res: Int) = this?.let { ContextCompat.getColor(it, res) }


// region Resources

fun Context.getStringArray(resId: Int): Array<String> = resources.getStringArray(resId)

fun Context.getQuantityString(
  @PluralsRes pluralId: Int,
  quantity: Int,
  vararg formatArgs: Any
) = resources.getQuantityString(pluralId, quantity, *formatArgs)

fun Context.getDimensionPixelSize(@DimenRes resId: Int) = resources.getDimensionPixelSize(resId)
fun Context.getInteger(@IntegerRes resId: Int) = resources.getInteger(resId)

// endregion