package ru.boronin.common.view.viewpager.transformer

import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

class FadeAnimationPageTransformer : ViewPager.PageTransformer {

  override fun transformPage(page: View, position: Float) {
    when {
      position <= -1.0F || position >= 1.0F -> {
        page.translationX = page.width * position
        page.alpha = 0.0F
      }

      position == 0.0F -> {
        page.translationX = page.width * position
        page.alpha = 1.0F
      }

      else -> {
        page.translationX = page.width * -position
        page.alpha = 1.0F - abs(position)
      }
    }
  }
}