package ru.boronin.common.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.withStyledAttributes


/**
 * Created by Sergey Boronin on 09.12.2019.
 */
class BgColorTextView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

  private var bgLineColor = Color.TRANSPARENT

  init {
    context.withStyledAttributes(attrs,
      R.styleable.BgColorTextView
    ) {
      if (hasValue(R.styleable.BgColorTextView_bgc_backgroundLineColor)) {
        getColorStateList(R.styleable.BgColorTextView_bgc_backgroundLineColor)?.also { bgLineColor = it.defaultColor }
      }
    }
  }

  override fun draw(canvas: Canvas) {
    val lineCount = layout.lineCount
    val rect = Rect()
    val paint = Paint()
    paint.color = bgLineColor
    for (i in 0 until lineCount) {
      rect.top = layout.getLineTop(i)
      rect.left = layout.getLineLeft(i).toInt()
      rect.right = layout.getLineRight(i).toInt()
      rect.bottom = (layout.getLineBottom(i) - if (i + 1 == lineCount) 0f else layout.spacingAdd).toInt()
      canvas.drawRect(rect, paint)
    }
    super.draw(canvas)
  }
}