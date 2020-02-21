package ru.boronin.common.extension.core

import android.app.Activity
import android.app.Application
import android.os.Bundle

fun Application.registerActivityLifecycleCallbacks(
  pausedFun: ((Activity) -> Unit)? = null,
  startedFun: ((Activity) -> Unit)? = null,
  saveInstanceStateFun: ((Activity, Bundle) -> Unit)? = null,
  stoppedFun: ((Activity) -> Unit)? = null,
  createdFun: ((Activity, Bundle?) -> Unit)? = null,
  resumedFun: ((Activity) -> Unit)? = null,
  destroyedFun: ((Activity) -> Unit)? = null
) {
  registerActivityLifecycleCallbacks(
    object : Application.ActivityLifecycleCallbacks {

      override fun onActivityPaused(activity: Activity) {
        pausedFun?.invoke(activity)
      }

      override fun onActivityStarted(activity: Activity) {
        startedFun?.invoke(activity)
      }

      override fun onActivityDestroyed(activity: Activity) {
        destroyedFun?.invoke(activity)
      }

      override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        saveInstanceStateFun?.invoke(activity, outState)
      }

      override fun onActivityStopped(activity: Activity) {
        stoppedFun?.invoke(activity)
      }

      override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        createdFun?.invoke(activity, savedInstanceState)
      }

      override fun onActivityResumed(activity: Activity) {
        resumedFun?.invoke(activity)
      }
    }
  )
}