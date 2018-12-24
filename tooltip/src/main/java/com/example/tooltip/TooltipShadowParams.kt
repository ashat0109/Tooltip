package com.example.tooltip


/**
 * Created by askhat on 28/11/18.
 */
private const val SHADOW_PADDING = 2
private const val SHADOW_RADIUS = 2
private const val SHADOW_OFFSET_X = 0
private const val SHADOW_OFFSET_Y = 1

data class TooltipShadowParams(
        var shouldShowShadow: Boolean = true,
        var shadowColorId: Int = R.color.tooltip_shadow_color,
        var shadowPadding: Int = SHADOW_PADDING,
        var shadowRadius: Int = SHADOW_RADIUS,
        var shadowOffsetX: Int = SHADOW_OFFSET_X,
        var shadowOffsetY: Int = SHADOW_OFFSET_Y
)