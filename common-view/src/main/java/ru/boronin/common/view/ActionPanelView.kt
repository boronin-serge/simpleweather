package ru.boronin.common.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.FontRes
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.action_panel_view.view.*
import ru.boronin.common.extension.widget.animationChangeColor
import ru.boronin.common.extension.widget.getColorStateList
import ru.boronin.common.extension.widget.getFont
import ru.boronin.common.utils.DEFAULT_STRING
import ru.boronin.core.android.view.base.BaseFrameLayout
import java.util.*

class ActionPanelView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : BaseFrameLayout(context, attrs, defStyleAttr) {
  private var negativeTextColor = Pair(
    getColorStateList(R.color.normalActionTextColor),
    getColorStateList(R.color.disableActionTextColor)
  )

  private var positiveTextColor = Pair(
    getColorStateList(R.color.normalActionTextColor),
    getColorStateList(R.color.disableActionTextColor)
  )

  init {
    context.withStyledAttributes(attrs,
      R.styleable.ActionPanelView
    ) {

      // region All

      if (hasValue(R.styleable.ActionPanelView_apv_textSize)) {
        setTextSize(
          this.getDimensionPixelSize(R.styleable.ActionPanelView_apv_textSize, 0)
            .toFloat()
        )
      }

      if (hasValue(R.styleable.ActionPanelView_apv_textColor)) {
        getColorStateList(R.styleable.ActionPanelView_apv_textColor)?.also {
          setTextColor(it, false)
        }
      }

      if (hasValue(R.styleable.ActionPanelView_apv_disableTextColor)) {
        getColorStateList(R.styleable.ActionPanelView_apv_disableTextColor)?.also {
          setDisableTextColor(it)
        }
      }

      if (hasValue(R.styleable.ActionPanelView_apv_textFont)) {
        setTextFont(getResourceId(R.styleable.ActionPanelView_apv_textFont, 0))
      }

      // endregion


      // region Negative

      if (hasValue(R.styleable.ActionPanelView_apv_negativeTextSize)) {
        setNegativeTextSize(
          this.getDimensionPixelSize(R.styleable.ActionPanelView_apv_negativeTextSize, 0)
            .toFloat()
        )
      }

      if (hasValue(R.styleable.ActionPanelView_apv_negativeTextColor)) {
        getColorStateList(R.styleable.ActionPanelView_apv_negativeTextColor)?.also {
          setNegativeTextColor(it, false)
        }
      }

      if (hasValue(R.styleable.ActionPanelView_apv_negativeDisableTextColor)) {
        getColorStateList(R.styleable.ActionPanelView_apv_negativeDisableTextColor)?.also {
          setNegativeDisableTextColor(it)
        }
      }

      if (hasValue(R.styleable.ActionPanelView_apv_negativeText)) {
        setNegativeText(getString(R.styleable.ActionPanelView_apv_negativeText) ?: DEFAULT_STRING)
      }

      if (hasValue(R.styleable.ActionPanelView_apv_negativeTextFont)) {
        setNegativeTextFont(
          getResourceId(
            R.styleable.ActionPanelView_apv_negativeTextFont,
            0
          )
        )
      }

      if (hasValue(R.styleable.ActionPanelView_apv_negativeTextVisible)) {
        setNegativeTextVisible(
          getBoolean(
            R.styleable.ActionPanelView_apv_negativeTextVisible,
            true
          )
        )
      }

      if (hasValue(R.styleable.ActionPanelView_apv_negativeTextEnable)) {
        setNegativeTextEnable(
          getBoolean(
            R.styleable.ActionPanelView_apv_negativeTextEnable,
            true
          ),
          false
        )
      }

      // endregion


      // region Positive

      if (hasValue(R.styleable.ActionPanelView_apv_positiveTextSize)) {
        setPositiveTextSize(
          this.getDimensionPixelSize(R.styleable.ActionPanelView_apv_positiveTextSize, 0)
            .toFloat()
        )
      }

      if (hasValue(R.styleable.ActionPanelView_apv_positiveTextColor)) {
        getColorStateList(R.styleable.ActionPanelView_apv_positiveTextColor)?.also {
          setPositiveTextColor(it, false)
        }
      }

      if (hasValue(R.styleable.ActionPanelView_apv_positiveDisableTextColor)) {
        getColorStateList(R.styleable.ActionPanelView_apv_positiveDisableTextColor)?.also {
          setPositiveDisableTextColor(it)
        }
      }

      if (hasValue(R.styleable.ActionPanelView_apv_positiveText)) {
        setPositiveText(getString(R.styleable.ActionPanelView_apv_positiveText) ?: DEFAULT_STRING)
      }

      if (hasValue(R.styleable.ActionPanelView_apv_positiveTextFont)) {
        setPositiveTextFont(
          getResourceId(
            R.styleable.ActionPanelView_apv_positiveTextFont,
            0
          )
        )
      }

      if (hasValue(R.styleable.ActionPanelView_apv_positiveTextVisible)) {
        setPositiveTextVisible(
          getBoolean(
            R.styleable.ActionPanelView_apv_positiveTextVisible,
            true
          )
        )
      }

      if (hasValue(R.styleable.ActionPanelView_apv_positiveTextEnable)) {
        setPositiveTextEnable(
          getBoolean(
            R.styleable.ActionPanelView_apv_positiveTextEnable,
            true
          ),
          false
        )
      }

      // endregion
    }
  }


  // region All

  fun setTextSize(size: Float) {
    setNegativeTextSize(size)
    setPositiveTextSize(size)
  }

  fun setTextColor(stateList: ColorStateList, animate: Boolean = true) {
    setNegativeTextColor(stateList, animate)
    setPositiveTextColor(stateList, animate)
  }

  fun setDisableTextColor(stateList: ColorStateList) {
    setNegativeDisableTextColor(stateList)
    setPositiveDisableTextColor(stateList)
  }

  fun setTextFont(@FontRes res: Int) {
    setNegativeTextFont(res)
    setPositiveTextFont(res)
  }

  // endregion


  // region Negative

  fun setNegativeTextSize(size: Float) {
    tvNegativeButton?.textSize(size)
  }

  fun setNegativeTextColor(stateList: ColorStateList, animate: Boolean = true) {
    tvNegativeButton?.textColor(stateList, negativeTextColor, animate) { negativeTextColor = it }
  }

  fun setNegativeDisableTextColor(stateList: ColorStateList) {
    negativeTextColor = Pair(negativeTextColor.first, stateList)
  }

  fun setNegativeText(text: String) {
    tvNegativeButton?.text(text)
  }

  fun setNegativeTextFont(@FontRes res: Int) {
    tvNegativeButton?.font(res)
  }

  fun setNegativeTextVisible(visible: Boolean) {
    tvNegativeButton?.isVisible = visible
  }

  fun setNegativeTextEnable(enable: Boolean, animate: Boolean = true) {
    tvNegativeButton?.enable(negativeTextColor, enable, animate)
  }

  fun negativeButtonClickListener(listenerFun: () -> Unit) {
    tvNegativeButton?.setOnClickListener { listenerFun.invoke() }
  }

  // endregion


  // region Positive

  fun setPositiveTextSize(size: Float) {
    tvPositiveButton?.textSize(size)
  }

  fun setPositiveTextColor(stateList: ColorStateList, animate: Boolean = true) {
    tvPositiveButton?.textColor(stateList, positiveTextColor, animate) { positiveTextColor = it }
  }

  fun setPositiveDisableTextColor(stateList: ColorStateList) {
    positiveTextColor = Pair(positiveTextColor.first, stateList)
  }

  fun setPositiveText(text: String) {
    tvPositiveButton?.text(text)
  }

  fun setPositiveTextFont(@FontRes res: Int) {
    tvPositiveButton?.font(res)
  }

  fun setPositiveTextVisible(visible: Boolean) {
    tvPositiveButton?.isVisible = visible
  }

  fun setPositiveTextEnable(enable: Boolean, animate: Boolean = true) {
    tvPositiveButton?.enable(positiveTextColor, enable, animate)
  }

  fun positiveButtonClickListener(listenerFun: () -> Unit) {
    tvPositiveButton?.setOnClickListener { listenerFun.invoke() }
  }

  // endregion


  // region Private

  private fun TextView.textSize(size: Float) {
    setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
  }

  private fun TextView.textColor(
    stateList: ColorStateList,
    textColor: Pair<ColorStateList?, ColorStateList?>,
    animate: Boolean,
    updateColorFun: (Pair<ColorStateList, ColorStateList>) -> Unit
  ) {
    if (isEnabled) {
      if (animate) {
        animationChangeColor(stateList)
      } else {
        setTextColor(stateList)
      }
    }
    updateColorFun.invoke(Pair(stateList, textColor.second!!))
  }

  private fun TextView.text(text: String) {
    setText(text.toUpperCase(Locale.getDefault()))
  }

  private fun TextView.font(@FontRes res: Int) {
    if (!isInEditMode) {
      typeface = getFont(res)
    }
  }

  private fun TextView.enable(
    textColor: Pair<ColorStateList?, ColorStateList?>,
    enable: Boolean,
    animate: Boolean
  ) {
    isEnabled = enable

    val color = if (enable) textColor.first else textColor.second
    color?.also {
      if (animate) {
        animationChangeColor(it)
      } else {
        setTextColor(it)
      }
    }
  }

  // endregion


  override fun getLayoutId() = R.layout.action_panel_view
}