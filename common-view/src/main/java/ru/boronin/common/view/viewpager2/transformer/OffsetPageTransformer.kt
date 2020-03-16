package ru.boronin.common.view.viewpager2.transformer

import android.view.View
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2

/**
 * Created by Sergey Boronin on 04.12.2019.
 */
class OffsetPageTransformer(
  private val pageOffset: Int,
  private val pageMargin: Int
) : ViewPager2.PageTransformer {
  override fun transformPage(page: View, position: Float) {
    val viewPager = page.parent.parent as ViewPager2
    val offset = position * -(2 * pageOffset + pageMargin)
    if (viewPager.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
      if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
        page.translationX = -offset
      } else {
        page.translationX = offset
      }
    } else {
      page.translationY = offset
    }
  }
}