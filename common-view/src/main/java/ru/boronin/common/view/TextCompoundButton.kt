package ru.boronin.common.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.LinearLayout
import androidx.annotation.FontRes
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.withStyledAttributes
import kotlinx.android.synthetic.main.text_checkbox.view.*
import ru.boronin.common.extension.widget.attach
import ru.boronin.common.extension.widget.checkedChangeListener
import ru.boronin.common.extension.widget.getFont
import ru.boronin.common.utils.DEFAULT_STRING
import ru.boronin.common.view.TextCompoundButton.CompoundButtonType.*
import ru.boronin.core.android.view.base.BaseFrameLayout

class TextCompoundButton @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : BaseFrameLayout(context, attrs, defStyleAttr) {
  private var compoundButton: CompoundButton? = null

  init {
    context.withStyledAttributes(attrs,
        R.styleable.TextCompoundButton
    ) {
      val compoundButtonInt = getInt(R.styleable.TextCompoundButton_tcb_compoundButton, CHECKBOX.ordinal)
      val compoundButtonType = CompoundButtonType.find(compoundButtonInt)
      setCompoundButton(compoundButtonType)

      if (hasValue(R.styleable.TextCompoundButton_tcb_textColor)) {
        getColorStateList(R.styleable.TextCompoundButton_tcb_textColor)?.also { setTextColor(it) }
      }

      if (hasValue(R.styleable.TextCompoundButton_tcb_textSize)) {
        setTextSize(
          this.getDimensionPixelSize(R.styleable.TextCompoundButton_tcb_textSize, 0)
            .toFloat()
        )
      }

      if (hasValue(R.styleable.TextCompoundButton_tcb_textFont)) {
        setTextFont(getResourceId(R.styleable.TextCompoundButton_tcb_textFont, 0))
      }

      if (hasValue(R.styleable.TextCompoundButton_tcb_text)) {
        setText(getString(R.styleable.TextCompoundButton_tcb_text) ?: DEFAULT_STRING)
      }

      if (hasValue(R.styleable.TextCompoundButton_tcb_checkBoxTint)) {
        getColorStateList(R.styleable.TextCompoundButton_tcb_checkBoxTint)?.also {
          setCheckBoxTint(it)
        }
      }

      if (hasValue(R.styleable.TextCompoundButton_tcb_textPaddingStart)) {
        setTextPadding(
          start = getDimensionPixelSize(
              R.styleable.TextCompoundButton_tcb_textPaddingStart,
            0
          )
        )
      }

      if (hasValue(R.styleable.TextCompoundButton_tcb_textPaddingTop)) {
        setTextPadding(
          top = getDimensionPixelSize(R.styleable.TextCompoundButton_tcb_textPaddingTop, 0)
        )
      }

      if (hasValue(R.styleable.TextCompoundButton_tcb_textPaddingEnd)) {
        setTextPadding(
          end = getDimensionPixelSize(R.styleable.TextCompoundButton_tcb_textPaddingEnd, 0)
        )
      }

      if (hasValue(R.styleable.TextCompoundButton_tcb_textPaddingBottom)) {
        setTextPadding(
          bottom = getDimensionPixelSize(
              R.styleable.TextCompoundButton_tcb_textPaddingBottom,
            0
          )
        )
      }
    }

    compoundButton?.attach(this)
  }


  // region Api

  fun checkedChangeListener(listenerFun: (Boolean) -> Unit) {
    compoundButton?.checkedChangeListener(listenerFun)
  }

  fun isChecked() = compoundButton?.isChecked ?: false

  fun setCompoundButton(type: CompoundButtonType) {
    compoundButton = createCompoundButton(type)
    vgCompoundButton?.removeAllViews()
    vgCompoundButton?.addView(compoundButton)
  }

  // endregion


  // region Private

  private fun setTextColor(stateList: ColorStateList) {
    tvText?.setTextColor(stateList)
  }

  private fun setTextSize(size: Float) {
    tvText?.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
  }

  private fun setTextFont(@FontRes res: Int) {
    if (!isInEditMode) {
      tvText?.typeface = getFont(res)
    }
  }

  private fun setText(text: CharSequence) {
    tvText?.text = text
  }

  private fun setCheckBoxTint(tint: ColorStateList) {
    compoundButton?.buttonTintList = tint
  }

  private fun setTextPadding(
    start: Int = tvText.paddingStart,
    top: Int = tvText.paddingTop,
    end: Int = tvText.paddingEnd,
    bottom: Int = tvText.paddingBottom
  ) {
    tvText?.setPaddingRelative(start, top, end, bottom)
  }

  private fun createCompoundButton(type: CompoundButtonType) = when (type) {
    CHECKBOX -> AppCompatCheckBox(context)
    RADIOBUTTON -> AppCompatRadioButton(context)
    SWITCH -> SwitchCompat(context)
  }.apply {
    layoutParams = LinearLayout.LayoutParams(
      ViewGroup.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT
    )
  }

  // endregion

  override fun getLayoutId() = R.layout.text_checkbox

  enum class CompoundButtonType {
    CHECKBOX,
    RADIOBUTTON,
    SWITCH;

    companion object {
      fun find(type: Int) = when(type) {
        CHECKBOX.ordinal -> CHECKBOX
        RADIOBUTTON.ordinal -> RADIOBUTTON
        else -> SWITCH
      }
    }
  }
}