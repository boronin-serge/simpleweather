package ru.boronin.common.extension.widget

import androidx.viewpager.widget.ViewPager

fun ViewPager.setPageTransformer(transformer: ViewPager.PageTransformer) {
  setPageTransformer(false, transformer)
}