package ru.boronin.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlin.math.abs


/**
 * Created by Sergey Boronin on 22.10.2019.
 */
class HorizontalSkipRefreshLayout : SwipeRefreshLayout {
  private var touchSlop: Int = 0
  private var prevX: Float = 0f

  constructor(ctx: Context) : super(ctx) {
    touchSlop = ViewConfiguration.get(ctx).scaledTouchSlop
  }

  constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
    touchSlop = ViewConfiguration.get(ctx).scaledTouchSlop
  }

  override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
    when (event.action) {
      MotionEvent.ACTION_DOWN -> prevX = event.x
      MotionEvent.ACTION_MOVE -> {
        val eventX = event.x
        val xDiff = abs(eventX - prevX)

        if (xDiff > touchSlop) {
          return false
        }
      }
    }

    return super.onInterceptTouchEvent(event)
  }
}