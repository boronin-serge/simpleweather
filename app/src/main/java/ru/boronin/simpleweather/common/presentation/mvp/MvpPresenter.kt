package ru.boronin.simpleweather.common.presentation.mvp

/**
 * Created by Sergey Boronin on 29.01.2020.
 */
interface MvpPresenter<V> {
    fun attachView(view: V)
    fun detachView()
}