package ru.boronin.common.plugins

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import ru.boronin.common.extension.core.findColor
import ru.boronin.common.extension.core.findDrawable
import ru.boronin.core.android.view.delegate.UIDelegatePlugin

val UIDelegatePlugin<Fragment>.context: Context?
  get() = target?.activity

fun UIDelegatePlugin<Fragment>.getString(@StringRes res: Int) = context?.getString(res)
fun UIDelegatePlugin<Fragment>.getDrawable(@DrawableRes res: Int) = context?.findDrawable(res)
fun UIDelegatePlugin<Fragment>.getColor(@ColorRes res: Int) = context?.findColor(res)