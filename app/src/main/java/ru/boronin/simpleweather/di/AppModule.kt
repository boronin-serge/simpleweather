package ru.boronin.simpleweather.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.boronin.common.network.OkHttpWrapper
import ru.boronin.common.network.RetrofitWrapper
import ru.boronin.core.api.schedulers.AppSchedulers
import ru.boronin.core.api.schedulers.SchedulersProvider
import ru.boronin.simpleweather.data.databse.CacheSource
import ru.boronin.simpleweather.data.databse.ForecastDatabase
import ru.boronin.simpleweather.data.network.ApiConfig.BASE_URL
import ru.boronin.simpleweather.data.network.NetworkSource

/**
 * Created by Sergey Boronin on 06.03.2020.
 */

@Module
class AppModule {

  @Provides
  fun provideRetrofit() = RetrofitWrapper(BASE_URL, OkHttpWrapper())

  @Provides
  fun provideNetworkSource(retrofitWrapper: RetrofitWrapper) = NetworkSource(retrofitWrapper)

  @Provides
  fun provideCacheSource(database: ForecastDatabase) = CacheSource(database)

  @Provides
  fun provideRxSchedulerProvider(): SchedulersProvider = AppSchedulers()

  @Provides
  fun provideDatabase(context: Context) = ForecastDatabase.getDatabase(context)
}
