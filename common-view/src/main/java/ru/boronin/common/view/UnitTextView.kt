package ru.boronin.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.withStyledAttributes

class UnitTextView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
  private var valueTextView: TextView? = null
  private var unitTextView: TextView? = null

  init {
    orientation = HORIZONTAL

    valueTextView = createValueText()
    unitTextView = createUnitText()

    addView(valueTextView)
    addView(unitTextView)

    context.withStyledAttributes(attrs,
      R.styleable.UnitTextView
    ) {
      val valueText = getString(R.styleable.UnitTextView_utv_valueText).orEmpty()
      setValueText(valueText)

      val unitText = getString(R.styleable.UnitTextView_utv_unitText).orEmpty()
      setUnitText(unitText)
    }
  }


  // region Api

  fun setValueText(value: String) {
    valueTextView?.text = value
  }

  fun setUnitText(unit: String) {
    unitTextView?.text = unit
  }

  // endregion


  // region Private

  private fun createValueText() = createTextView(R.style.valueTextStyle)
  private fun createUnitText() = createTextView(R.style.unitTextStyle)
  private fun createTextView(@StyleRes res: Int) = AppCompatTextView(ContextThemeWrapper(context, res))
  //private fun createTextView(@StyleRes res: Int) = AppCompatTextView(context, null, res)

  // endregion
}