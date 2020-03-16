package ru.boronin.common.view.viewpager2.transformer

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs
import kotlin.math.max

/**
 * Created by Sergey Boronin on 04.12.2019.
 */
class TransitionPageTransformer : ViewPager2.PageTransformer {
  override fun transformPage(page: View, position: Float) {
    when {
      position <= 1 && position >= -1 -> {
        val scaleFactor = max(0.9f, 1 - abs(position) / 4)
        page.scaleX = scaleFactor
        page.scaleY = scaleFactor
      }
    }
  }
}