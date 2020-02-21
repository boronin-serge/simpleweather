package ru.boronin.common.plugins.toolbar

import android.view.MenuItem
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import ru.boronin.commonplugins.R

interface ToolbarUIDelegatePlugin {
  fun setToolbarTitle(@StringRes res: Int)
  fun setToolbarTitle(title: String?)
  fun setToolbarSubTitle(@StringRes res: Int)
  fun setToolbarSubTitle(subTitle: String?)
  fun setToolbarColor(@ColorRes res: Int)
  fun setVisibleToolbar(visible: Boolean)
  fun setToolbarBackButtonCallback(callbackFun: (() -> Unit)?)
  fun setMenu(@MenuRes res: Int)
  fun setMenuItemCallback(menuItemFun: ((MenuItem) -> Unit)?)
  fun setVisibleToolbarShadow(visible: Boolean)
  fun transparentToolbar()

  fun setVisibleToolbarBackButton(
    visible: Boolean,
    @ColorRes iconTint: Int = R.color.toolbarPlugin_defaultBackButtonTintColor,
    @DrawableRes icon: Int = R.drawable.ic_arrow_back_black_24dp
  )
}