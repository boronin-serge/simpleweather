package ru.boronin.simpleweather.common.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.boronin.simpleweather.di.ActivityComponent
import ru.boronin.common.navigation.AppNavigatorHandlerImpl
import ru.boronin.common.navigation.ScreenResult
import ru.boronin.common.navigation.ScreenResultProvider
import ru.boronin.common.plugins.loading.LoadingUIDelegatePlugin
import ru.boronin.common.plugins.loading.LoadingUIDelegatePluginImpl
import ru.boronin.common.plugins.popup.PopupUIDelegatePlugin
import ru.boronin.common.plugins.popup.PopupUIDelegatePluginImpl
import ru.boronin.common.plugins.toolbar.ToolbarUIDelegatePlugin
import ru.boronin.common.plugins.toolbar.ToolbarUIDelegatePluginImpl
import ru.boronin.core.android.view.delegate.UIDelegatePlugin
import ru.boronin.core.android.view.delegate.UIDelegatePluginEvent
import ru.boronin.core.api.navigator.BackListener
import ru.boronin.simpleweather.features.main.ui.MainActivity
import ru.boronin.simpleweather.R

/**
 * Created by Sergey Boronin on 25.12.2019.
 */

private const val DEFAULT_LAYOUT = R.layout.base_fragment

abstract class BaseFragment(
    private val popupPlugin: PopupUIDelegatePluginImpl = PopupUIDelegatePluginImpl(),
    private val loadingPlugin: LoadingUIDelegatePluginImpl = LoadingUIDelegatePluginImpl(
        R.id.vgLoading
    ),
    private val toolbarPlugin: ToolbarUIDelegatePluginImpl = ToolbarUIDelegatePluginImpl(
        R.id.toolbar
    )
) : Fragment(),
    BackListener,
    ScreenResultProvider,
    PopupUIDelegatePlugin by popupPlugin,
    ToolbarUIDelegatePlugin by toolbarPlugin,
    LoadingUIDelegatePlugin by loadingPlugin {

    private val plugins = mutableListOf<UIDelegatePlugin<Fragment>>()

    init {
        initUIDelegatePlugins()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as? MainActivity)?.activityComponent?.let { initDagger(it) }

        sendUIDelegatePluginEvent(UIDelegatePluginEvent.OnAttach)
    }

    override fun onDetach() {
        super.onDetach()

        sendUIDelegatePluginEvent(UIDelegatePluginEvent.OnDetach)

        clearDependencies()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(DEFAULT_LAYOUT, container, false).apply {
        val userView = inflater.inflate(getLayout(), this as ViewGroup, false)
        findViewById<ViewGroup>(R.id.container).addView(userView)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sendUIDelegatePluginEvent(UIDelegatePluginEvent.OnViewBound(view))
    }

    override fun onDestroy() {
        super.onDestroy()

        sendUIDelegatePluginEvent(UIDelegatePluginEvent.Release)
    }

    abstract fun initDagger(activityComponent: ActivityComponent)

    abstract fun clearDependencies()

    abstract fun getLayout(): Int

    fun getLocalNavigator() = AppNavigatorHandlerImpl(
        activity?.supportFragmentManager!!,
        getLayout()
    )

    // region ScreenResultProvider

    override var result: ScreenResult? = null

    override fun setResult(resultCode: Int, data: Intent?) {
        result?.apply {
            requestCode?.let { targetFragment?.onActivityResult(it, resultCode, data) }
        }
    }

    // endregion ScreenResultProvider


    // region BackListener

    override fun back() = true

    // endregion


    // region Private

    private fun sendUIDelegatePluginEvent(event: UIDelegatePluginEvent) {
        plugins.forEach { it.onUIDelegatePluginEvent(event) }
    }

    private fun initUIDelegatePlugins() {
        addUIDelegatePlugin(loadingPlugin)
        addUIDelegatePlugin(toolbarPlugin)
        addUIDelegatePlugin(popupPlugin)
    }

    private fun addUIDelegatePlugin(plugin: UIDelegatePlugin<Fragment>) {
        with(plugins) {
            plugin.target = this@BaseFragment
            add(plugin)
        }
    }

    // endregion

}