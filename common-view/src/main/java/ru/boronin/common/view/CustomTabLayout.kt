package ru.boronin.common.view

import android.content.Context
import android.graphics.Color
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.custom_tab_layout.view.*
import kotlinx.android.synthetic.main.tab_layout.view.*
import ru.boronin.common.extension.widget.fadeOutIn
import ru.boronin.core.android.view.base.BaseFrameLayout

/**
 * Created by Sergey Boronin on 19.02.2020.
 */
class CustomTabLayout @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    private val defStyleAttr: Int = 0
) : BaseFrameLayout(context, attrs, defStyleAttr) {

    private val tabs: MutableList<TabView> = mutableListOf()
    private var activeTextColor: Int = Color.GRAY
    private var activeIconColor: Int = Color.GRAY
    private val inactiveTextColor: Int = Color.GRAY
    private val inactiveIconColor: Int = Color.GRAY
    private var listener: (Int) -> Unit = {}

    override fun getLayoutId() = R.layout.custom_tab_layout

    fun addTabs(tabsData: List<TabData>) {
        tabsData.forEachIndexed { index, tabData -> addTab(tabData, index) }
        post {
            val itemWidth = tabLayout.measuredWidth / tabsData.size
            tabs.forEach { it.setTabWidth(itemWidth) }
            selectedArea.apply { layoutParams = layoutParams.apply { width = itemWidth } }
        }
    }

    fun activateTab(index: Int) {
        if (index in tabs.indices && tabs[index].isActive().not()) {
            tabs.forEach {
                it.setEnable(false)
                it.setTextColor(inactiveTextColor)
                it.setIconColor(inactiveIconColor)
            }
            tabs[index].apply {
                setEnable(true)
                setTextColor(activeTextColor)
                setIconColor(activeIconColor)
            }
        }

        selectedArea.fadeOutIn {
            selectedArea.x = tabs[index].x + tabLayout.x
        }
    }

    fun setActiveIconColor(color: Int) {
        activeIconColor = color
        tabs.forEach { it.setIconColor(color) }
    }

    fun setActiveTextColor(color: Int) {
        activeTextColor = color
        tabs.forEach { it.setTextColor(color) }
    }

    fun setOnTabClickListener(listener: (Int) -> Unit) {
        this.listener = listener
    }

    // region private

    private fun addTab(tabData: TabData, index: Int) {
        val tabView = TabView(context, attrs, defStyleAttr).apply {
            setTabData(tabData)
            setOnClickListener {
                listener.invoke(index)
                activateTab(index)
            }
        }
        tabs.add(tabView)
        tabLayout.addView(tabView)
    }

    // endregion
}

data class TabData(val iconRes: Int, val title: String)

class TabView @JvmOverloads  constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var isActive = false

    init {
        LayoutInflater.from(context).inflate(R.layout.tab_layout, this, true)
    }

    fun isActive() = isActive

    fun setEnable(enable: Boolean) {
        isActive = enable
        if (enable) {
            tvTitle.alpha = 0f
            tvTitle.isVisible = true
            tvTitle.animate().setStartDelay(100).setDuration(300).alpha(1f).start()
        } else {
            tvTitle.alpha = 0f
            tvTitle.isVisible = false
        }
        enableTitle(enable)
        enableIcon(enable)
    }

    fun setTabData(tabData: TabData) {
        setIcon(tabData.iconRes)
        setTitle(tabData.title)
    }

    fun setIcon(iconRes: Int) = ivLogo.setImageResource(iconRes)
  fun setTitle(title: String) {
    tvTitle.text = title
  }
    fun setIconColor(color: Int) = ivLogo.setColorFilter(color)
    fun setTextColor(color: Int) = tvTitle.setTextColor(color)
    fun setTabWidth(width: Int) {
        layoutParams = layoutParams.apply { this.width = width }
    }

    // region private

    private fun enableTitle(enable: Boolean) {
        val set = ConstraintSet()
        set.clone(container)

        if (enable) {
            set.clear(R.id.tvTitle, ConstraintSet.START)
            set.connect(R.id.tvTitle, ConstraintSet.START, R.id.ivLogo, ConstraintSet.END)
        } else {
            set.clear(R.id.tvTitle, ConstraintSet.START)
            set.connect(R.id.tvTitle, ConstraintSet.START, R.id.container, ConstraintSet.START)
        }

        animateConstraint(set)
    }

    private fun enableIcon(enable: Boolean) {
        val set = ConstraintSet()
        set.clone(container)

        if (enable) {
            set.clear(R.id.ivLogo, ConstraintSet.END)
            set.connect(R.id.ivLogo, ConstraintSet.END, R.id.tvTitle, ConstraintSet.START)
        } else {
            set.clear(R.id.ivLogo, ConstraintSet.END)
            set.connect(R.id.ivLogo, ConstraintSet.END, R.id.container, ConstraintSet.END)
        }

        animateConstraint(set)
    }

    private fun animateConstraint(set: ConstraintSet) {
        val transition = AutoTransition()
        transition.duration = 100
        transition.interpolator = AccelerateDecelerateInterpolator()

        TransitionManager.beginDelayedTransition(container, transition)
        set.applyTo(container)
    }

    // endregion
}


