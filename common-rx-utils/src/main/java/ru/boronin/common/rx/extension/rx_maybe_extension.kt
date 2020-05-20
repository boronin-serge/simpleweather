package ru.boronin.common.rx.extension

import io.reactivex.Maybe
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.boronin.core.api.schedulers.SchedulersProvider

fun <T> Maybe<T>.schedulers(
  provider: SchedulersProvider
) = schedulers(provider.io(), provider.ui())

fun <T> Maybe<T>.schedulers() = schedulers(Schedulers.io(), AndroidSchedulers.mainThread())

fun <T> Maybe<T>.schedulers(
  subscribeOnScheduler: Scheduler,
  observeOnScheduler: Scheduler
): Maybe<T> = compose {
  it.subscribeOn(subscribeOnScheduler)
    .observeOn(observeOnScheduler)
}

fun <T> Maybe<T>.progress(progressFun: (Boolean) -> Unit): Maybe<T> = compose {
  it.doOnSubscribe { progressFun.invoke(true) }
    .doAfterTerminate { progressFun.invoke(false) }
}