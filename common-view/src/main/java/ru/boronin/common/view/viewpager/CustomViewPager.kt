package ru.boronin.common.view.viewpager

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.content.withStyledAttributes
import androidx.viewpager.widget.ViewPager
import ru.boronin.common.view.R

private const val DEFAULT_PAGING = true

class CustomViewPager @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null
) : ViewPager(context, attrs) {
  var paging = DEFAULT_PAGING

  init {
    context.withStyledAttributes(attrs, R.styleable.CustomViewPager) {
      paging = getBoolean(R.styleable.CustomViewPager_paging, DEFAULT_PAGING)
    }
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(event: MotionEvent) = if (paging) {
    super.onTouchEvent(event)
  } else {
    false
  }

  override fun onInterceptTouchEvent(event: MotionEvent) = if (paging) {
    super.onInterceptTouchEvent(event)
  } else {
    false
  }
}