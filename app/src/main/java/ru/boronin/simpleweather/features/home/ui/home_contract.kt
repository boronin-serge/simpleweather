package ru.boronin.simpleweather.features.home.ui

import ru.boronin.simpleweather.common.presentation.mvp.MvpView
import ru.boronin.simpleweather.model.common.presentation.ForecastModel

interface HomeView : MvpView {
    fun checkLocationPermissions()
    fun updateView(model: ForecastModel)
}

interface HomeAction {
    fun checkWeatherAction()
}