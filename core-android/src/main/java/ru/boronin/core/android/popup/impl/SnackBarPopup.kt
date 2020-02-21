package ru.boronin.core.android.popup.impl

import android.app.Activity
import android.content.Context
import android.graphics.Point
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.IdRes
import ru.boronin.common.extension.widget.findViewTraversal
import ru.boronin.common.utils.getNavigationBarSize
import ru.boronin.core.android.popup.Popup
import ru.boronin.core.android.popup.PopupDuration

/**
 * Implementation [Popup] for [Snackbar]
 *
 * Created by ivasi on 01.03.2017.
 */
private const val SNACKBAR_TEXT_MAX_LINES = 3

class SnackBarPopup(context: Context?) : Popup {
  private var view: View? = null
  private var snackbar: Snackbar? = null
  private var onActionClickListener: Popup.OnActionClickListener? = null
  private var navBarSize: Point

  init {
    if (context is Activity) {
      view = context.window
        .decorView
    }

    navBarSize = context?.getNavigationBarSize() ?: Point()
  }

  override fun show(
    msg: CharSequence?,
    duration: PopupDuration,
    action: String?,
    @IdRes rootId:Int
  ) {
    show(rootId, msg, duration.snackbar, action)
  }

  private fun show(@IdRes rootId:Int, msg: CharSequence?, duration: Int, action: String? = null) {
    val view = view?.findViewById(rootId) ?: view ?: return
    if (msg != null) {
      dismiss()
      snackbar = Snackbar.make(view, msg, duration)

      snackbar?.findTextView()
        ?.maxLines = SNACKBAR_TEXT_MAX_LINES

      if (action != null) {
        snackbar?.setAction(action) { onActionClickListener?.onActionClick() }
      }

      snackbar?.show()
    }
  }

  override fun setOnActionClickListener(onActionClickListener: Popup.OnActionClickListener?) {
    this.onActionClickListener = onActionClickListener
  }

  override fun dismiss() {
    snackbar?.dismiss()
  }

  override fun getPopupHeight(height: (Int) -> Unit) {
    snackbar?.addCallback(
      object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
        override fun onShown(transientBottomBar: Snackbar?) {
          val view = snackbar?.view
          height(view?.measuredHeight ?: 0)
          snackbar?.removeCallback(this)
        }
      }
    )
  }

  private fun Snackbar.findTextView(): TextView? {
    return view.findViewTraversal {
      // Since action is a button, and Button extends TextView,
      // Need to make sure this is the message TextView, not the
      // action Button view.
      it is TextView && it !is Button
    } as? TextView
  }
}