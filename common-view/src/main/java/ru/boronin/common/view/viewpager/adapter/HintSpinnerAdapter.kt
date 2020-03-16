package ru.boronin.common.view.viewpager.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

class HintSpinnerAdapter(
  context: Context,
  resource: Int,
  objects: Array<String>,
  @ColorRes private val hintColor: Int,
  @ColorRes private val normalColor: Int = android.R.color.black
) : ArrayAdapter<String>(context, resource, objects) {

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val view = super.getView(position, convertView, parent)

    with(view.findViewById<TextView>(android.R.id.text1)) {
      text = getItem(position)

      val colorRes = if (position == 0) hintColor else normalColor
      val color = ContextCompat.getColor(view.context, colorRes)
      setTextColor(color)
    }

    return view
  }
}