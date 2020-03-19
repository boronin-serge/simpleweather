package ru.boronin.simpleweather.features.futureforecast.di

import dagger.Module
import dagger.Provides
import ru.boronin.simpleweather.features.futureforecast.navigator.FutureForecastNavigator
import ru.boronin.simpleweather.features.futureforecast.navigator.FutureForecastNavigatorImpl
import ru.boronin.simpleweather.features.futureforecast.ui.FutureForecastFragment
import ru.boronin.common.navigation.AppNavigatorHandlerImpl
import ru.boronin.core.api.navigator.NavigatorHandler

@Module
class FutureForecastModule {
    
    @Provides
    fun provideNavigator(
        navigatorHandler: NavigatorHandler,
        fragment: FutureForecastFragment
    ): FutureForecastNavigator {
        return FutureForecastNavigatorImpl().apply {
            globalHandler = navigatorHandler
            localHandler = fragment.getLocalNavigator()
        }
    }
}