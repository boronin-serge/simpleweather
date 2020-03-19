package ru.boronin.simpleweather.features.futureforecast.ui

import ru.boronin.simpleweather.common.presentation.mvp.BasePresenter
import ru.boronin.simpleweather.features.futureforecast.navigator.FutureForecastNavigator
import javax.inject.Inject

class FutureForecastPresenter @Inject constructor(
    private val navigator: FutureForecastNavigator
) : BasePresenter<FutureForecastView>(), FutureForecastAction {
    
}