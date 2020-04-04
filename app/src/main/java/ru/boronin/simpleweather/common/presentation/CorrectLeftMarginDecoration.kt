package ru.boronin.simpleweather.common.presentation

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Sergey Boronin on 17.04.2019.
 */
class CorrectLeftMarginDecoration(private val padding: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        // Скорректировать отступ вверху списка
        with(outRect) {
            left = if (parent.getChildAdapterPosition(view) == 0) padding else 0
            top = padding
            right = padding
            bottom = padding
        }
    }
}