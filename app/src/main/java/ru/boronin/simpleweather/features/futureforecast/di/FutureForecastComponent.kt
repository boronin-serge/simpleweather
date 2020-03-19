package ru.boronin.simpleweather.features.futureforecast.di

import dagger.BindsInstance
import dagger.Subcomponent
import ru.boronin.simpleweather.features.futureforecast.ui.FutureForecastFragment
import ru.boronin.simpleweather.features.home.di.HomeModule
import javax.inject.Singleton

@Subcomponent(modules = [FutureForecastModule::class])
interface FutureForecastComponent {
    
    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: FutureForecastFragment): FutureForecastComponent
    }

    fun inject(fragment: FutureForecastFragment)
}   
        
        