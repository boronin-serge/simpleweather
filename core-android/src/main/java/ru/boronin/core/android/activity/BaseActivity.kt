package ru.boronin.core.android.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
  abstract val layoutId: Int

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initializeInjector()

    setContentView(layoutId)
  }

  protected open fun initializeInjector() { }
}