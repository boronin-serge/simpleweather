package ru.boronin.simpleweather.common.presentation.mvp

import android.os.Bundle
import android.view.View
import ru.boronin.simpleweather.common.presentation.BaseFragment

/**
 * Created by Sergey Boronin on 29.01.2020.
 */
abstract class BaseView<V : MvpView, P : MvpPresenter<V>, C> : MvpDelegateCallback<V, P>,
    BaseFragment(),
    MvpView {

    protected var component: C? = null

    override val view: V
        get() = this as V

    abstract fun onViewBound(view: View)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onViewBound(view)

        presenter.attachView(this.view)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.detachView()
    }

    override fun clearDependencies() {
        component = null
    }
}