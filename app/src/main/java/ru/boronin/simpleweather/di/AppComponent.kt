package ru.boronin.simpleweather.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Sergey Boronin on 14.01.2020.
 */

@Singleton
@Component
interface AppComponent {

    fun activityFactory(): ActivityComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}