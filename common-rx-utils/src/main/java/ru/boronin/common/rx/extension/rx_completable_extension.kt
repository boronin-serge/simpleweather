package ru.boronin.common.rx.extension

import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.boronin.common.rx.utils.SimpleDisposableObserver
import ru.boronin.core.api.schedulers.SchedulersProvider

fun Completable.schedulers(
  provider: SchedulersProvider
) = schedulers(provider.io(), provider.ui())

fun Completable.schedulers() = schedulers(Schedulers.io(), AndroidSchedulers.mainThread())

fun Completable.schedulers(
  subscribeOnScheduler: Scheduler,
  observeOnScheduler: Scheduler
): Completable = compose {
  it.subscribeOn(subscribeOnScheduler)
    .observeOn(observeOnScheduler)
}

fun Completable.progress(progressFun: (Boolean) -> Unit): Completable = compose {
  it.doOnSubscribe { progressFun.invoke(true) }
    .doOnTerminate { progressFun.invoke(false) }
}

fun Completable.subscribeWith(
  onErrorFun: ((Throwable) -> Unit)? = null,
  onCompleteFun: (() -> Unit)? = null
) = subscribeWith(
  object : SimpleDisposableObserver<Any>() {

    override fun onError(e: Throwable) {
      e.printStackTrace()
      onErrorFun?.invoke(e)
    }

    override fun onComplete() {
      onCompleteFun?.invoke()
    }
  }
)