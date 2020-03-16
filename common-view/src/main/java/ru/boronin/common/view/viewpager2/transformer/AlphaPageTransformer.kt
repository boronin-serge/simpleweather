package ru.boronin.common.view.viewpager2.transformer

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs
import kotlin.math.max

/**
 * Created by Sergey Boronin on 04.12.2019.
 */
class AlphaPageTransformer : ViewPager2.PageTransformer {
  override fun transformPage(page: View, position: Float) {
    when {
      position < -1 ->
        page.alpha = 0.1f
      position <= 1 -> {
        page.alpha = max(0.2f, 1 - abs(position))
      }
      else -> page.alpha = 0.1f
    }
  }
}