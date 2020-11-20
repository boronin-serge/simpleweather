package ru.boronin.simpleweather.features.main.navigator

import ru.boronin.common.navigation.AppNavigator
import ru.boronin.simpleweather.features.home.ui.HomeFragment

/**
 * Created by Sergey Boronin on 27.01.2020.
 */
class MainNavigatorImpl : AppNavigator(), MainNavigator {
  override fun openHome() {
    globalHandler?.setRoot(HomeFragment())
  }
}
