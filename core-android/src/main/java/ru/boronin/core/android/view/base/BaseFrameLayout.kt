package ru.boronin.core.android.view.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes

@Suppress("LeakingThis")
abstract class BaseFrameLayout @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

  init {
    val view = LayoutInflater.from(context)
      .inflate(getLayoutId(), null, false)
    this.addView(view)
    this.onViewBound(view, attrs, defStyleAttr)
  }

  protected open fun onViewBound(view: View?, attrs: AttributeSet?, defStyleAttr: Int) { }
  @LayoutRes abstract fun getLayoutId(): Int
}