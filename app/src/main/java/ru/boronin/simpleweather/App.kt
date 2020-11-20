package ru.boronin.simpleweather

import android.app.Application
import com.facebook.stetho.Stetho
import ru.boronin.simpleweather.di.AppComponent
import ru.boronin.simpleweather.di.DaggerAppComponent

open class App : Application() {

  val appComponent: AppComponent by lazy {
    DaggerAppComponent.factory().create(applicationContext)
  }

  override fun onCreate() {
    super.onCreate()

    if (BuildConfig.DEBUG) {
      initStetho()
    }
  }

  private fun initStetho() {
    Stetho.initializeWithDefaults(this)
  }
}
