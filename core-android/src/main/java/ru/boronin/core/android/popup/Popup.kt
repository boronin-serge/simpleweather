package ru.boronin.core.android.popup

import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import com.google.android.material.snackbar.Snackbar

interface Popup {
  fun show(
      msg: CharSequence?,
      duration: PopupDuration = PopupDuration.SHORT,
      action: String? = null,
      @IdRes rootId: Int = View.NO_ID
  )
  fun dismiss()
  fun getPopupHeight(height: (Int) -> Unit)
  fun setOnActionClickListener(onActionClickListener: OnActionClickListener?)

  interface OnActionClickListener {
    fun onActionClick()
  }
}

enum class PopupDuration(val snackbar: Int, val toast: Int) {
  INDEFINITE(Snackbar.LENGTH_INDEFINITE, Integer.MIN_VALUE),
  SHORT(Snackbar.LENGTH_SHORT, Toast.LENGTH_SHORT),
  LONG(Snackbar.LENGTH_LONG, Toast.LENGTH_LONG)
}