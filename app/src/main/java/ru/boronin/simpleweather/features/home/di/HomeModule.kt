package ru.boronin.simpleweather.features.home.di

import dagger.Module
import dagger.Provides
import ru.boronin.core.api.location.LocationProvider
import ru.boronin.core.api.navigator.NavigatorHandler
import ru.boronin.simpleweather.domain.interactor.WeatherInteractor
import ru.boronin.simpleweather.features.home.navigator.HomeNavigator
import ru.boronin.simpleweather.features.home.navigator.HomeNavigatorImpl
import ru.boronin.simpleweather.features.home.ui.HomeFragment
import ru.boronin.simpleweather.features.home.ui.HomePresenter

@Module
class HomeModule {
    
    @Provides
    fun provideNavigator(
        navigatorHandler: NavigatorHandler,
        fragment: HomeFragment
    ): HomeNavigator {
        return HomeNavigatorImpl().apply {
            globalHandler = navigatorHandler
            localHandler = fragment.getLocalNavigator()
        }
    }

    @Provides
    fun providePresenter(
        navigator: HomeNavigator,
        interactor: WeatherInteractor,
        locationProvider: LocationProvider
    ) = HomePresenter(navigator, interactor, locationProvider)


}