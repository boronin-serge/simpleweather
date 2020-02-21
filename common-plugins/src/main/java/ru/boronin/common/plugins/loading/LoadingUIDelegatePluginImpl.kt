package ru.boronin.common.plugins.loading

import android.view.View
import androidx.annotation.IdRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import ru.boronin.common.utils.delegate.weakReference
import ru.boronin.core.android.view.delegate.UIDelegatePlugin
import ru.boronin.core.android.view.delegate.UIDelegatePluginEvent
import ru.boronin.core.android.view.delegate.findViewById

class LoadingUIDelegatePluginImpl(
  @IdRes private val loadingViewId: Int
) : UIDelegatePlugin<Fragment>(), LoadingUIDelegatePlugin {
  private var loadingView by weakReference<View>()


  // region UIDelegatePlugin

  override fun onUIDelegatePluginEvent(event: UIDelegatePluginEvent) {
    super.onUIDelegatePluginEvent(event)

    when (event) {
      is UIDelegatePluginEvent.OnViewBound -> loadingView = event.findViewById(loadingViewId)
      is UIDelegatePluginEvent.Release -> loadingView = null
      else -> { }
    }
  }

  // endregion


  // region LoadingUIDelegatePlugin

  override fun setVisibleLoading(visible: Boolean) {
    loadingView?.isVisible = visible
  }

  // endregion
}