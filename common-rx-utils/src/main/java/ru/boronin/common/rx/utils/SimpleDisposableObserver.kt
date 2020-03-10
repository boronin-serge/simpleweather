package ru.boronin.common.rx.utils

open class SimpleDisposableObserver<T> : DisposableAnyObserver<T>() {
  override fun onError(e: Throwable) { }
  override fun onComplete() { }
  override fun onNext(data: T) { }
}