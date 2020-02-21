package ru.boronin.simpleweather.features.home.di

import dagger.BindsInstance
import dagger.Subcomponent
import ru.boronin.simpleweather.features.home.ui.HomeFragment

@Subcomponent(modules = [HomeModule::class])
interface HomeComponent {
    
    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: HomeFragment): HomeComponent
    }

    fun inject(fragment: HomeFragment)
}   
        
        