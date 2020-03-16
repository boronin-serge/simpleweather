package ru.boronin.common.view.viewpager2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerFragmentAdapter(
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {

    private val items: MutableList<Fragment> = mutableListOf()

    override fun createFragment(position: Int) = items[position]

    override fun getItemCount() = items.size

    fun update(items: List<Fragment>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}
