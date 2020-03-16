package ru.boronin.common.view.viewpager2.transformer

import android.view.View
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

/**
 * Created by Sergey Boronin on 04.12.2019.
 */
class RotatePageTransformer : ViewPager2.PageTransformer {

  private val rotationFactor = 20 // more value -> more impact
  private val scaleFactor = 3 // more value -> less impact
  private val yShiftFactor = 12 // more value -> more impact

  override fun transformPage(page: View, position: Float) {
    val viewPager = page.parent.parent as ViewPager2
    val offset = position * rotationFactor
    if (viewPager.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
      if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
        page.rotation = -offset
      } else {
        page.rotation = offset
      }
      page.scaleX = 1 - abs(position / scaleFactor)
      page.scaleY = 1 - abs(position / scaleFactor)
    } else {
      page.rotation = offset
    }
    page.translationY = abs(offset * yShiftFactor)
  }
}