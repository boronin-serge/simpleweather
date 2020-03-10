package ru.boronin.core.api.schedulers

import io.reactivex.schedulers.TestScheduler

class TestSchedulers : SchedulersProvider {
  override fun single() = TestScheduler()
  override fun ui() = TestScheduler()
  override fun computation() = TestScheduler()
  override fun trampoline() = TestScheduler()
  override fun newThread() = TestScheduler()
  override fun io() = TestScheduler()
}