package ru.boronin.common.extension.widget

import android.view.View
import android.widget.CompoundButton

fun CompoundButton.checkedChangeListener(checkedFun: (Boolean) -> Unit) {
  setOnCheckedChangeListener { _, isChecked -> checkedFun.invoke(isChecked) }
}

fun CompoundButton.attach(view: View?) {
  view?.setOnClickListener { isChecked = !isChecked }
}