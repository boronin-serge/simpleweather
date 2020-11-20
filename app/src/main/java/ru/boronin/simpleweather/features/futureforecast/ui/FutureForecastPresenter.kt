package ru.boronin.simpleweather.features.futureforecast.ui

import ru.boronin.simpleweather.common.presentation.mvp.BasePresenter
import ru.boronin.simpleweather.domain.interactor.WeatherInteractor
import ru.boronin.simpleweather.features.futureforecast.navigator.FutureForecastNavigator
import javax.inject.Inject

class FutureForecastPresenter @Inject constructor(
  private val navigator: FutureForecastNavigator,
  private val interactor: WeatherInteractor
) : BasePresenter<FutureForecastView>(), FutureForecastAction {

  override fun onFirstViewAttach() {
    interactor.getLastLoadedWeather()?.let {
      view?.showForecast(it)
    }
  }

  override fun backAction() {
    navigator.back()
  }
}
