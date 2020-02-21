package ru.boronin.core.android.popup.impl

import android.content.Context
import android.widget.Toast
import androidx.annotation.IdRes
import ru.boronin.core.android.popup.Popup
import ru.boronin.core.android.popup.PopupDuration
import ru.boronin.core.android.popup.PopupDuration.INDEFINITE

class ToastPopup(private val context: Context) :
  Popup {
  private var toast: Toast? = null

  override fun setOnActionClickListener(onActionClickListener: Popup.OnActionClickListener?) { }

  override fun show(
    msg: CharSequence?,
    duration: PopupDuration,
    action: String?,
    @IdRes rootId:Int
  ) {
    if (duration == INDEFINITE) {
      throw UnsupportedOperationException("ToastPopup not supports 'indefinite' popup duration")
    }

    if (action != null) {
      throw UnsupportedOperationException("ToastPopup not supports 'action'")
    }

    dismiss()
    toast = Toast.makeText(context, msg, duration.toast)
    toast?.show()
  }

  override fun dismiss() {
    toast?.cancel()
  }

  override fun getPopupHeight(height: (Int) -> Unit) {
    val view = toast?.view
    view?.post { height(view.measuredHeight) }
  }
}