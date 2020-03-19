package ru.boronin.simpleweather.di

import androidx.fragment.app.FragmentActivity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.boronin.simpleweather.features.futureforecast.di.FutureForecastComponent
import ru.boronin.simpleweather.features.home.di.HomeComponent
import ru.boronin.simpleweather.features.main.ui.MainActivity
import javax.inject.Singleton

/**
 * Created by Sergey Boronin on 14.01.2020.
 */

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance activity: FragmentActivity,
            @BindsInstance containerId: Int
        ): ActivityComponent
    }

    fun homeFactory(): HomeComponent.Factory
    fun futureForecastFactory(): FutureForecastComponent.Factory

    fun inject(activity: MainActivity)
}