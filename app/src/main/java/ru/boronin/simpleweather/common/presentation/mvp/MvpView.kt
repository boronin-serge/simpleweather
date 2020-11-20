package ru.boronin.simpleweather.common.presentation.mvp

/**
 * Created by Sergey Boronin on 29.01.2020.
 */
interface MvpView {
  fun showPopup(msg: String)
  fun setVisibleLoading(visible: Boolean)
  fun hasConnection(): Boolean
}
