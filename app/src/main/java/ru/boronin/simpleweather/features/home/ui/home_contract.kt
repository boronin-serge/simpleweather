package ru.boronin.simpleweather.features.home.ui

import ru.boronin.simpleweather.common.presentation.mvp.MvpView
import ru.boronin.simpleweather.model.common.presentation.ForecastModel
import ru.boronin.simpleweather.model.common.presentation.HourForecastModel

interface HomeView : MvpView {
    fun checkLocationPermissions()
    fun updateView(model: ForecastModel)
    fun updateList(data: List<HourForecastModel>)
    fun setWeatherMode(weatherMode: HomeFragment.WeatherMode)
}

interface HomeAction {
    fun checkWeatherAction()
    fun showTodayWeatherAction()
    fun showTomorrowWeatherAction()
    fun showNextFiveDaysWeatherAction()
}