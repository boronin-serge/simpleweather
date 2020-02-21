package ru.boronin.common.extension.widget

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

fun Spinner.itemSelectedListener(selectedFun: (Int) -> Unit) {
  onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
      selectedFun.invoke(position)
    }
  }
}