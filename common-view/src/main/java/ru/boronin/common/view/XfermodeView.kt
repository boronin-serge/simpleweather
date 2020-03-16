package ru.boronin.common.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import ru.boronin.common.view.XfermodeView.TypeSize.CUSTOM
import ru.boronin.common.view.XfermodeView.TypeSize.FIT

private const val DEFAULT_SIZE = 500 // px

class XfermodeView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
  private var paint = Paint()

  private var widthTransparent = DEFAULT_SIZE
  private var heightTransparent = DEFAULT_SIZE

  private var typeSize = CUSTOM

  private var marginStart = 0
  private var marginTop = 0
  private var marginEnd = 0
  private var marginBottom = 0

  init {
    context.withStyledAttributes(attrs,
      R.styleable.XfermodeView
    ) {
      widthTransparent = getDimensionPixelSize(
        R.styleable.XfermodeView_widthTransparent,
        DEFAULT_SIZE
      )

      heightTransparent = getDimensionPixelSize(
        R.styleable.XfermodeView_heightTransparent,
        DEFAULT_SIZE
      )

      val typeInt = getInt(R.styleable.XfermodeView_typeSize, CUSTOM.ordinal)
      typeSize =
        TypeSize.find(typeInt)

      marginStart = getDimensionPixelSize(R.styleable.XfermodeView_marginStart, 0)
      marginTop = getDimensionPixelSize(R.styleable.XfermodeView_marginTop, 0)
      marginEnd = getDimensionPixelSize(R.styleable.XfermodeView_marginEnd, 0)
      marginBottom = getDimensionPixelSize(R.styleable.XfermodeView_marginBottom, 0)
    }

    setLayerType(LAYER_TYPE_SOFTWARE, null)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    setWillNotDraw(false)
  }

  override fun onDraw(canvas: Canvas?) {
    val start = if (typeSize == FIT) {
      (0 + marginStart).toFloat()
    } else {
      ((width - widthTransparent) / 2).toFloat() + marginStart
    }

    val top = if (typeSize == FIT) {
      (0 + marginTop).toFloat()
    } else {
      ((height - heightTransparent) / 2).toFloat() + marginTop
    }

    val end = if (typeSize == FIT) {
      start + (width - start - marginEnd)
    } else {
      start + widthTransparent - marginEnd
    }

    val bottom = if (typeSize == FIT) {
      top + (height - top - marginBottom)
    } else {
      top + heightTransparent - marginBottom
    }

    canvas?.drawRect(start, top, end, bottom, paint)
  }

  private enum class TypeSize {
    FIT,
    CUSTOM;

    companion object {
      fun find(type: Int) = when (type) {
        FIT.ordinal -> FIT
        else -> CUSTOM
      }
    }
  }
}