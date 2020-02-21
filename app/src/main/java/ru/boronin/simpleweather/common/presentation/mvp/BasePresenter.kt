package ru.boronin.simpleweather.common.presentation.mvp

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.boronin.common.utils.delegate.weakReference

/**
 * Created by Sergey Boronin on 29.01.2020.
 */
abstract class BasePresenter<V : MvpView> : MvpPresenter<V> {
    private var isFirstViewAttach = false

    protected var view by weakReference<V>()

    protected open val subscriptions = CompositeDisposable()


    // region override

    override fun attachView(view: V) {
        this.view = view

        if (!isFirstViewAttach) {
            onFirstViewAttach()
            isFirstViewAttach = true
        }
    }

    override fun detachView() {
        view = null
        clearSubscriptions()
    }

    // endregion


    // region protected

    protected open fun onFirstViewAttach() { }

    protected open fun clearSubscriptions() {
        subscriptions.clear()
    }

    // endregion
}

infix fun CompositeDisposable.add(disposable: Disposable) {
    add(disposable)
}