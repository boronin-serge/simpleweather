package ru.boronin.simpleweather.features.futureforecast.ui

import ru.boronin.simpleweather.common.presentation.mvp.MvpView
import ru.boronin.simpleweather.model.common.presentation.ForecastModel

interface FutureForecastView : MvpView {
    fun showForecast(data: ForecastModel)
}

interface FutureForecastAction {
    fun backAction()
}