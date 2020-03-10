package ru.boronin.common.rx.extension

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.boronin.common.rx.utils.SimpleDisposableObserver
import ru.boronin.core.api.schedulers.SchedulersProvider

fun <T> Single<T>.schedulers(
  provider: SchedulersProvider
) = schedulers(provider.io(), provider.ui())

fun <T> Single<T>.schedulers() = schedulers(Schedulers.io(), AndroidSchedulers.mainThread())

fun <T> Single<T>.schedulers(
  subscribeOnScheduler: Scheduler,
  observeOnScheduler: Scheduler
): Single<T> = compose {
  it.subscribeOn(subscribeOnScheduler)
    .observeOn(observeOnScheduler)
}

fun <T> Single<T>.progress(progressFun: (Boolean) -> Unit): Single<T> = compose {
  it.doOnSubscribe { progressFun.invoke(true) }
    .doAfterTerminate { progressFun.invoke(false) }
}

fun <T> Single<T>.subscriberWith(
  onErrorFun: ((Throwable) -> Unit)? = null,
  onCompleteFun: (() -> Unit)? = null,
  onNextFun: ((T) -> Unit)? = null
) = subscribeWith(
  object : SimpleDisposableObserver<T>() {

    override fun onError(e: Throwable) {
      e.printStackTrace()
      onErrorFun?.invoke(e)
    }

    override fun onNext(data: T) {
      onNextFun?.invoke(data)
    }

    override fun onComplete() {
      onCompleteFun?.invoke()
    }
  }
)