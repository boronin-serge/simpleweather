package ru.boronin.common.plugins.popup

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import ru.boronin.core.android.popup.showPopup
import ru.boronin.core.android.view.delegate.UIDelegatePlugin


class PopupUIDelegatePluginImpl : UIDelegatePlugin<Fragment>(), PopupUIDelegatePlugin {

  // region PopupUIDelegatePlugin

  override fun showPopup(msg: String) {
    target?.activity?.showPopup(msg)
  }

  override fun showPopup(@StringRes msgRes: Int) {
    val msg = target?.activity?.getString(msgRes)
    target?.activity?.showPopup(msg)
  }

  // endregion
}