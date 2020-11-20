package ru.boronin.simpleweather.di

import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import dagger.Module
import dagger.Provides
import ru.boronin.common.navigation.AppNavigatorHandlerImpl
import ru.boronin.core.api.location.LocationProvider
import ru.boronin.core.api.location.impl.FusedLocationProvider
import ru.boronin.core.api.navigator.NavigatorHandler
import ru.boronin.core.api.schedulers.SchedulersProvider
import ru.boronin.simpleweather.common.presentation.image.ImageLoader
import ru.boronin.simpleweather.common.presentation.image.ImageLoaderImpl
import ru.boronin.simpleweather.data.databse.CacheSource
import ru.boronin.simpleweather.data.network.NetworkSource
import ru.boronin.simpleweather.data.repository.WeatherRepositoryImpl
import ru.boronin.simpleweather.domain.interactor.WeatherInteractor
import ru.boronin.simpleweather.domain.repository.WeatherRepository
import ru.boronin.simpleweather.features.main.navigator.MainNavigator
import ru.boronin.simpleweather.features.main.navigator.MainNavigatorImpl
import ru.boronin.simpleweather.model.common.data.mapper.CurrentWeatherMapper
import ru.boronin.simpleweather.model.common.data.mapper.CurrentWeatherMapperImpl
import ru.boronin.simpleweather.model.common.data.mapper.DetailedWeatherMapper
import ru.boronin.simpleweather.model.common.data.mapper.DetailedWeatherMapperImpl
import ru.boronin.simpleweather.model.common.presentation.mapper.ForecastModelMapper
import ru.boronin.simpleweather.model.common.presentation.mapper.ForecastModelMapperImpl

/**
 * Created by Sergey Boronin on 14.01.2020.
 */

@Module
class ActivityModule {

  @Provides
  fun provideNavigator(activity: FragmentActivity, containerId: Int): NavigatorHandler {
    return AppNavigatorHandlerImpl(activity.supportFragmentManager, containerId)
  }

  @Provides
  fun provideMainNavigator(
    navigatorHandler: NavigatorHandler
  ): MainNavigator {
    return MainNavigatorImpl().apply {
      globalHandler = navigatorHandler
    }
  }

  @Provides
  fun provideFusedLocationProvider(activity: FragmentActivity): LocationProvider {
    return FusedLocationProvider(activity)
  }

  @Provides
  fun provideRepository(
    networkSource: NetworkSource,
    cacheSource: CacheSource,
    mapper: ForecastModelMapper
  ): WeatherRepository = WeatherRepositoryImpl(networkSource, cacheSource, mapper)

  @Provides
  fun provideCurrentWeatherMapper(): CurrentWeatherMapper = CurrentWeatherMapperImpl()

  @Provides
  fun provideDetailedWeatherMapper(
    currentWeatherMapper: CurrentWeatherMapper
  ): DetailedWeatherMapper {
    return DetailedWeatherMapperImpl(currentWeatherMapper)
  }

  @Provides
  fun provideForecastModelMapper(): ForecastModelMapper = ForecastModelMapperImpl()

  @Provides
  fun provideImageLoader(activity: FragmentActivity): ImageLoader {
    return ImageLoaderImpl(Glide.with(activity))
  }

  @ActivityScope
  @Provides
  fun provideInteractor(
    weatherRepository: WeatherRepository,
    schedulers: SchedulersProvider,
    currentWeatherMapper: CurrentWeatherMapper,
    detailedWeatherMapper: DetailedWeatherMapper
  ) = WeatherInteractor(
    weatherRepository,
    currentWeatherMapper,
    detailedWeatherMapper,
    schedulers
  )
}
