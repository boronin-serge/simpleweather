package ru.boronin.common.navigation

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.boronin.core.api.navigator.NavigatorHandler

/**
 * Created by Sergey Boronin on 26.12.2019.
 */
class AppNavigatorHandlerImpl(
    private val fragmentManager: FragmentManager,
    private val containerId: Int
) : NavigatorHandler {

    private val inAnimator = R.animator.fade_in_short
    private val outAnimator = android.R.animator.fade_out

    override fun open(deepLink: String) {
        throw UnsupportedOperationException()
    }

    override fun open(obj: Any?, tag: String) {
        if (obj is Fragment) {
            pushFragment(obj, tag)
        }
    }

    override fun openForResult(obj: Any?, requestCode: Int, tag: String) {
        val new = obj as? Fragment
        if (new is ScreenResultProvider) {
            val old = getLastFragment()
            new.result = ScreenResult(requestCode) // We've just set requestCode. Data will be set later
            new.setTargetFragment(old, requestCode)
            pushFragment(new, tag)
        }
    }

    override fun back() {
        fragmentManager.popBackStack()
    }

    override fun backWithDeliveryResult() {
        if (getStackSize() > 0) {
            val current = getLastFragment()
            val targetController = current?.targetFragment
            val screenResult = (current as? ScreenResultProvider)?.result

            screenResult?.apply {
                if (requestCode != null) {
                    targetController?.onActivityResult(requestCode, Activity.RESULT_OK, data)
                }
            }
        }
    }

    override fun backToTag(tag: String, deliveryResult: Boolean) {
        throw UnsupportedOperationException()
    }

    override fun backToRoot(deliveryResult: Boolean) {
        if (getStackSize() > 0) {
            for (i in 1..getStackSize()) {
                fragmentManager.popBackStack()
            }
        }
    }

    override fun pop(obj: Any?) {
        if (obj is Fragment) {
            fragmentManager.beginTransaction().remove(obj)
        }
    }

    override fun setRoot(obj: Any?) {
        if (obj is Fragment) {
            for(i in 0 .. getStackSize()) {
                fragmentManager.popBackStack()
            }
            pushFragment(obj)
        }
    }

    override fun replace(obj: Any) {
        if (obj is Fragment) {
            replaceFragment(obj)
        }
    }

    override fun getStackSize(): Int {
        return fragmentManager.backStackEntryCount
    }

    fun getLastFragment(): Fragment? {
        val index =  getStackSize() - 1
        return if (index > -1) {
            val tag =  fragmentManager.getBackStackEntryAt(index).name
            fragmentManager.findFragmentByTag(tag)
        } else {
            null
        }
    }

    // region private

    private fun pushFragment(fragment: Fragment, tag: String = "") {
        val notEmptyTag = if (tag.isEmpty()) {
            fragment.javaClass.simpleName
        } else {
            tag
        }

        fragmentManager.beginTransaction()
            .setCustomAnimations(inAnimator, outAnimator)
            .add(containerId, fragment, notEmptyTag)
            .addToBackStack(notEmptyTag)
            .commit()
    }

    private fun replaceFragment(fragment: Fragment, tag: String = "") {
        val notEmptyTag = if (tag.isEmpty()) {
            fragment.javaClass.simpleName
        } else {
            tag
        }

        fragmentManager.beginTransaction()
            .setCustomAnimations(inAnimator, outAnimator)
            .replace(containerId, fragment, notEmptyTag)
            .addToBackStack(notEmptyTag)
            .commit()
    }

    // endregion
}