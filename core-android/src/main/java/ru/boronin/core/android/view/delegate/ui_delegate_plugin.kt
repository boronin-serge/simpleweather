package ru.boronin.core.android.view.delegate

import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import ru.boronin.common.utils.delegate.weakReference

sealed class UIDelegatePluginEvent {
  class OnViewBound(val view: View?) : UIDelegatePluginEvent()
  object Release : UIDelegatePluginEvent()
  object OnAttach : UIDelegatePluginEvent()
  object OnDetach : UIDelegatePluginEvent()
}

abstract class UIDelegatePlugin<C> {
  var target: C? by weakReference()

  @CallSuper
  open fun onUIDelegatePluginEvent(event: UIDelegatePluginEvent) {
    when (event) {
      UIDelegatePluginEvent.Release -> target = null
      else -> { }
    }
  }
}

fun <T : View> UIDelegatePluginEvent.OnViewBound.findViewById(
  @IdRes viewId: Int
) = view?.findViewById<T>(viewId)