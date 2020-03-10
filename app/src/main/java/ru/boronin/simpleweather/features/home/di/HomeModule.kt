package ru.boronin.simpleweather.features.home.di

import dagger.Module
import dagger.Provides
import ru.boronin.core.api.location.LocationProvider
import ru.boronin.core.api.navigator.NavigatorHandler
import ru.boronin.core.api.schedulers.AppSchedulers
import ru.boronin.core.api.schedulers.SchedulersProvider
import ru.boronin.simpleweather.data.network.NetworkSource
import ru.boronin.simpleweather.data.repository.WeatherRepositoryImpl
import ru.boronin.simpleweather.domain.interactor.WeatherInteractor
import ru.boronin.simpleweather.domain.repository.WeatherRepository
import ru.boronin.simpleweather.features.home.navigator.HomeNavigator
import ru.boronin.simpleweather.features.home.navigator.HomeNavigatorImpl
import ru.boronin.simpleweather.features.home.ui.HomeFragment
import ru.boronin.simpleweather.features.home.ui.HomePresenter
import ru.boronin.simpleweather.model.common.data.mapper.WeatherMapperImpl

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

    @Provides
    fun provideRepository(source: NetworkSource): WeatherRepository = WeatherRepositoryImpl(source)

    @Provides
    fun provideInteractor(
        weatherRepository: WeatherRepository,
        schedulers: SchedulersProvider
    ) = WeatherInteractor(
        weatherRepository,
        WeatherMapperImpl(),
        schedulers
    )
}