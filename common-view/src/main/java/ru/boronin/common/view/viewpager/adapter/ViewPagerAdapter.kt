package ru.boronin.common.view.viewpager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.PagerAdapter

abstract class ViewPagerAdapter<T>() : PagerAdapter() {
  private var context: Context? = null
  @LayoutRes private var layoutRes: Int? = null
  private var items: List<T> = emptyList()

  constructor(
    context: Context,
    @LayoutRes layoutRes: Int,
    items: List<T> = listOf()
  ) : this(context, items) {
    this.layoutRes = layoutRes
  }

  constructor(context: Context, items: List<T> = listOf()) : this() {
    this.context = context
    this.items = items
  }

  companion object {
    fun <T> create(
      context: Context,
      @LayoutRes layoutRes: Int,
      items: List<T> = listOf(),
      bind: (view: View?, item: T?) -> Unit
    ) = object : ViewPagerAdapter<T>(context, layoutRes, items) {
      override fun onBind(view: View?, item: T?) {
        bind.invoke(view, item)
      }
    }

    fun <T> create(
      context: Context,
      items: List<T> = listOf(),
      bindView: (root: ViewGroup?) -> View,
      bind: (view: View?, item: T?) -> Unit
    ) = object : ViewPagerAdapter<T>(context, items) {
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
  ) = LayoutInflater.from(context).inflate(res, root, false)

  protected open fun onBindView(root: ViewGroup): View = inflate(layoutRes!!, root)
  abstract fun onBind(view: View?, item: T?)
}