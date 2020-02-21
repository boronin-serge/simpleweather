package ru.boronin.simpleweather

import android.app.Application
import ru.boronin.simpleweather.di.AppComponent
import ru.boronin.simpleweather.di.DaggerAppComponent

open class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}