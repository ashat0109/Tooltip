package com.example.tooltip

import android.view.Gravity

/**
 * Created by askhat on 28/11/18.
 */
data class TooltipOffsetParams(
        var align: Int = Gravity.CENTER,
        var offsetX: Int = 0,
        var offsetY: Int = 0
)