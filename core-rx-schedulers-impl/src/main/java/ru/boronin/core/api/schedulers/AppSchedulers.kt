package ru.boronin.core.api.schedulers

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppSchedulers : SchedulersProvider {
  override fun ui() = AndroidSchedulers.mainThread()
  override fun computation() = Schedulers.computation()
  override fun trampoline() = Schedulers.trampoline()
  override fun newThread() = Schedulers.newThread()
  override fun io() = Schedulers.io()
  override fun single() = Schedulers.single()
}