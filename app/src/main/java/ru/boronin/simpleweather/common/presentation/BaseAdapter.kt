package ru.boronin.simpleweather.common.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Sergey Boronin on 10.02.2020.
 */
abstract class BaseAdapter<V : RecyclerView.ViewHolder, D> : RecyclerView.Adapter<V>() {

    protected val items: MutableList<D> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = initViewHolder(
        LayoutInflater.from(parent.context).inflate(getItemLayout(), parent, false)
    )

    override fun getItemCount() = items.size

    abstract fun getItemLayout(): Int

    abstract fun initViewHolder(itemView: View): V

    fun update(items: List<D>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

}