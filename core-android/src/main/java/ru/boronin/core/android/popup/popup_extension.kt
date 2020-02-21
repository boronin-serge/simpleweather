package ru.boronin.core.android.popup

import android.content.Context
import android.view.View
import androidx.annotation.IdRes
import ru.boronin.core.android.popup.impl.SnackBarPopup
import ru.boronin.core.android.popup.impl.ToastPopup

/**
 * It will help me for easy {@link Toast} or {@link Snackbar}
 *
 * Use for {@link Snackbar} : {@link SnackBarPopup}
 *
 * Created by ivasi on 01.03.2017.
 */
fun Context.showPopup(
  msg: CharSequence?,
  duration: PopupDuration = PopupDuration.SHORT,
  action: String? = null,
  onActionClickListener: Popup.OnActionClickListener? = null,
  @IdRes resId:Int = View.NO_ID
) = getPopup().show(msg, duration, action, onActionClickListener, resId)

fun Context.showSnackBarPopup(
  msg: CharSequence?,
  duration: PopupDuration = PopupDuration.SHORT,
  action: String? = null,
  onActionClickListener: Popup.OnActionClickListener? = null,
  @IdRes resId:Int = View.NO_ID
) = SnackBarPopup(this).show(msg, duration, action, onActionClickListener, resId)

fun Context.showToastPopup(
  msg: CharSequence?,
  duration: PopupDuration = PopupDuration.SHORT,
  action: String? = null,
  @IdRes resId:Int = View.NO_ID
) = ToastPopup(this).show(msg, duration, action, resId)

fun Popup.show(
  msg: CharSequence?,
  duration: PopupDuration = PopupDuration.SHORT,
  action: String? = null,
  onActionClickListener: Popup.OnActionClickListener? = null,
  @IdRes resId:Int = View.NO_ID
) = this.apply {
  setOnActionClickListener(onActionClickListener)
  show(msg, duration, action, resId)
}

fun Context.getPopup() = ToastPopup(this)