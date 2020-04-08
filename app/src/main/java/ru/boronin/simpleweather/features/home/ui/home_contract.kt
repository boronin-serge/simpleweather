package ru.boronin.simpleweather.features.home.ui

import ru.boronin.simpleweather.common.presentation.mvp.MvpView
import ru.boronin.simpleweather.model.common.presentation.ForecastModel
import ru.boronin.simpleweather.model.common.presentation.HourForecastModel

interface HomeView : MvpView {
    fun checkLocationPermissions()
    fun updateView(model: ForecastModel)
    fun updateList(data: List<HourForecastModel>, animate: Boolean = true)
    fun setWeatherMode(weatherMode: HomeFragment.WeatherMode)
    fun showErrorPage()
    fun showErrorToast()
}

interface HomeAction {
    fun checkWeatherAction()
    fun showTodayWeatherAction()
    fun showTomorrowWeatherAction()
    fun showNextFiveDaysWeatherAction()
}