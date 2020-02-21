package ru.boronin.simpleweather.features.main.ui

import android.os.Bundle
import ru.boronin.common.navigation.AppNavigatorHandlerImpl
import ru.boronin.core.api.navigator.NavigatorHandler
import ru.boronin.simpleweather.R
import ru.boronin.simpleweather.common.presentation.BaseActivity
import ru.boronin.simpleweather.di.ActivityComponent
import ru.boronin.simpleweather.di.AppComponent
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var navigator: NavigatorHandler

    var activityComponent: ActivityComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        attachNavigator(navigator as AppNavigatorHandlerImpl)

        presenter.init()
    }

    override fun initDagger(appComponent: AppComponent) {
        activityComponent = appComponent
            .activityFactory()
            .create(this, R.id.container)

        activityComponent?.inject(this)
    }

    override fun clearDependencies() {
        activityComponent = null
    }
}
