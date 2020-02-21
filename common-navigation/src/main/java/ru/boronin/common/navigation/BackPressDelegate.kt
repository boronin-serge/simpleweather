package ru.boronin.common.navigation

/**
 * Created by Sergey Boronin on 04.02.2020.
 */
interface BackPressDelegate {
    fun attachNavigator(navigator: AppNavigatorHandlerImpl)
    fun backPressed(superFun: () -> Unit, hideAppFun: () -> Unit)
}