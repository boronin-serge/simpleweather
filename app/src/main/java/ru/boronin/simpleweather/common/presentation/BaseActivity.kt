package ru.boronin.simpleweather.common.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.boronin.common.navigation.BackPressDelegate
import ru.boronin.common.navigation.BackPressDelegateImpl
import ru.boronin.simpleweather.App
import ru.boronin.simpleweather.di.AppComponent

/**
 * Created by Sergey Boronin on 25.12.2019.
 */
abstract class BaseActivity : AppCompatActivity(), BackPressDelegate by BackPressDelegateImpl() {

  override fun onCreate(savedInstanceState: Bundle?) {
    initDagger((application as App).appComponent)
    super.onCreate(savedInstanceState)
  }

  override fun onDestroy() {
    super.onDestroy()

    clearDependencies()
  }

  override fun onBackPressed() {
    backPressed(
      {
        super.onBackPressed()
      },
      {
        moveTaskToBack(false)
      }
    )
  }

  abstract fun initDagger(appComponent: AppComponent)
  abstract fun clearDependencies()
}
