package ru.boronin.common.view.viewpager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.PagerAdapter

abstract class ViewPagerAdapter<T>() : PagerAdapter() {
  @LayoutRes private var layoutRes: Int? = null
  private var items: List<T> = emptyList()

  constructor(
    @LayoutRes layoutRes: Int,
    items: List<T> = listOf()
  ) : this(items) {
    this.layoutRes = layoutRes
  }

  constructor(items: List<T> = listOf()) : this() {
    this.items = items
  }

  companion object {
    fun <T> create(
      @LayoutRes layoutRes: Int,
      items: List<T> = listOf(),
      bind: (view: View?, item: T?) -> Unit
    ) = object : ViewPagerAdapter<T>(layoutRes, items) {
      override fun onBind(view: View?, item: T?) {
        bind.invoke(view, item)
      }
    }

    fun <T> create(
      items: List<T> = listOf(),
      bindView: (root: ViewGroup?) -> View,
      bind: (view: View?, item: T?) -> Unit
    ) = object : ViewPagerAdapter<T>(items) {
      override fun onBindView(root: ViewGroup) = bindView.invoke(root)

      override fun onBind(view: View?, item: T?) {
        bind.invoke(view, item)
      }
    }
  }

  override fun instantiateItem(
    collection: ViewGroup,
    position: Int
  ) = onBindView(collection).also {
    onBind(it, items[position])
    collection.addView(it)
  }

  override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
    collection.removeView(view as View)
  }

  override fun getCount(): Int = items.size
  override fun isViewFromObject(view: View, obj: Any): Boolean = view === obj
  override fun getItemPosition(obj: Any): Int = POSITION_NONE

  private fun inflate(
    @LayoutRes res: Int,
    root: ViewGroup
  ) = LayoutInflater.from(root.context).inflate(res, root, false)

  protected open fun onBindView(root: ViewGroup): View = inflate(layoutRes!!, root)
  abstract fun onBind(view: View?, item: T?)
}