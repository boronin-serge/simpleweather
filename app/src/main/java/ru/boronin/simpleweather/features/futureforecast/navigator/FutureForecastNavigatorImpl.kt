package ru.boronin.simpleweather.features.futureforecast.navigator

import ru.boronin.common.navigation.AppNavigator

class FutureForecastNavigatorImpl  : AppNavigator(), FutureForecastNavigator {
  override fun back() {
    globalHandler?.back()
  }
}