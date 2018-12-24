package com.example.tooltip

/**
 * Created by askhat on 28/11/18.
 */
private const val TOOLTIP_PADDING_LEFT = 12
private const val TOOLTIP_PADDING_RIGHT = 12
private const val TOOLTIP_PADDING_TOP = 10
private const val TOOLTIP_PADDING_BOTTOM = 12

data class TooltipPaddingParams(
        var left: Int = TOOLTIP_PADDING_LEFT,
        var right: Int = TOOLTIP_PADDING_RIGHT,
        var top: Int = TOOLTIP_PADDING_TOP,
        var bottom: Int = TOOLTIP_PADDING_BOTTOM
)