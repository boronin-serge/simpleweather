package ru.boronin.common.rx.utils

import io.reactivex.CompletableObserver
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableHelper
import io.reactivex.internal.util.EndConsumerHelper
import java.util.concurrent.atomic.AtomicReference

abstract class DisposableAnyObserver<T> : SingleObserver<T?>,
                                          Observer<T?>,
                                          CompletableObserver,
                                          Disposable {
  private val s = AtomicReference<Disposable>()

  override fun onSubscribe(d: Disposable) {
    if (EndConsumerHelper.setOnce(this.s, d, javaClass)) {
      onStart()
    }
  }

  /**
   * Called once the single upstream Disposable is set via onSubscribe.
   */
  fun onStart() { }

  override fun onSuccess(data: T) {
    onNext(data)
  }

  override fun isDisposed() = s.get() === DisposableHelper.DISPOSED

  override fun dispose() {
    DisposableHelper.dispose(s)
  }
}