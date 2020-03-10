package ru.boronin.common.rx.extension

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.boronin.common.rx.utils.SimpleDisposableObserver
import ru.boronin.core.api.schedulers.SchedulersProvider

fun <T> Observable<T>.schedulers(
  provider: SchedulersProvider
) = schedulers(provider.io(), provider.ui())

fun <T> Observable<T>.schedulers() = schedulers(Schedulers.io(), AndroidSchedulers.mainThread())

fun <T> Observable<T>.schedulers(
  subscribeOnScheduler: Scheduler,
  observeOnScheduler: Scheduler
): Observable<T> = compose {
  it.subscribeOn(subscribeOnScheduler)
    .observeOn(observeOnScheduler)
}

fun <T> Observable<T>.progress(progressFun: (Boolean) -> Unit): Observable<T> = compose {
  it.doOnSubscribe { progressFun.invoke(true) }
    .doOnComplete { progressFun.invoke(false) }
}

fun <T> Observable<T>.subscribeWith(
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