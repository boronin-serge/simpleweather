package ru.boronin.simpleweather.di

import androidx.fragment.app.FragmentActivity
import dagger.Module
import dagger.Provides
import ru.boronin.common.navigation.AppNavigatorHandlerImpl
import ru.boronin.core.api.navigator.NavigatorHandler
import ru.boronin.simpleweather.features.main.navigator.MainNavigator
import ru.boronin.simpleweather.features.main.navigator.MainNavigatorImpl

/**
 * Created by Sergey Boronin on 14.01.2020.
 */

@Module
class ActivityModule {

    @Provides
    fun provideNavigator(activity: FragmentActivity, containerId: Int): NavigatorHandler {
        return AppNavigatorHandlerImpl(activity.supportFragmentManager, containerId)
    }

    @Provides
    fun provideMainNavigator(
        navigatorHandler: NavigatorHandler
    ): MainNavigator {
        return MainNavigatorImpl().apply {
            globalHandler = navigatorHandler
        }
    }
}