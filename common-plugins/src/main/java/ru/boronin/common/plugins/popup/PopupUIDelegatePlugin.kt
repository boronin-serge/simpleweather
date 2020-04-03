package ru.boronin.common.plugins.popup

import androidx.annotation.StringRes

interface PopupUIDelegatePlugin {
  fun showPopup(msg: String)
  fun showPopup(@StringRes msgRes: Int)
}