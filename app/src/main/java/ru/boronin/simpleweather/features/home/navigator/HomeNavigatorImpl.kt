package ru.boronin.simpleweather.features.home.navigator

import ru.boronin.common.navigation.AppNavigator
import ru.boronin.simpleweather.features.futureforecast.ui.FutureForecastFragment

class HomeNavigatorImpl : AppNavigator(), HomeNavigator {
  override fun openFutureForecast() {
    globalHandler?.open(FutureForecastFragment())
  }
}
