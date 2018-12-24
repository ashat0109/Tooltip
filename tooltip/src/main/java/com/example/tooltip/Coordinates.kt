package com.example.tooltip

import android.view.View

/**
 * Created by askhat on 21/11/18.
 */
data class Coordinates(
        val view: View
) {
    val left: Int
    val top: Int
    val right: Int
    val bottom: Int

    init {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        left = location[0]
        right = left + view.width
        top = location[1]
        bottom = top + view.height
    }
}