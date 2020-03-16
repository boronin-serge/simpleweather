package ru.boronin.common.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.core.content.withStyledAttributes
import androidx.recyclerview.widget.RecyclerView
import ru.boronin.common.extension.primitive.getDimenPxProperty

/**
 * Created by Sergey Boronin on 13.02.2020.
 */
class CustomRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var path = Path()
    private lateinit var corners: FloatArray

    init {
        initCustomProperties(attrs)

        prepareForElevation()

        setWillNotDraw(false)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        path.addRoundRect(rect, corners, Path.Direction.CW)
        path.close()
    }

    override fun draw(canvas: Canvas) {
        canvas.save()
        canvas.clipPath(path)
        super.draw(canvas)
        canvas.restore()
    }


    // region private

    private fun prepareForElevation() {
        if (background == null) {
            val shape = GradientDrawable()
            shape.cornerRadius = corners.max() ?: 0f
            shape.setColor(Color.WHITE)
            background = shape
        }
    }

    private fun initCustomProperties(attrs: AttributeSet) {
        context.withStyledAttributes(attrs, R.styleable.CustomRecyclerView) {
            val tlRadius = getDimenPxProperty(R.styleable.CustomRecyclerView_topLeftCornerRadius)
            val trRadius = getDimenPxProperty(R.styleable.CustomRecyclerView_topRightCornerRadius)
            val blRadius = getDimenPxProperty(R.styleable.CustomRecyclerView_bottomLeftCornerRadius)
            val brRadius = getDimenPxProperty(R.styleable.CustomRecyclerView_bottomRightCornerRadius)

            corners = floatArrayOf(
                tlRadius, tlRadius, trRadius, trRadius,
                blRadius, blRadius, brRadius, brRadius
            )
        }
    }

    // endregion
}