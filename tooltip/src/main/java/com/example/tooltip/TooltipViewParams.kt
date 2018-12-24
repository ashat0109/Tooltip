package com.example.tooltip

import android.graphics.Color
import android.view.Gravity

/**
 * Created by askhat on 28/11/18.
 */
private const val CORNER_RADIUS = 3

data class TooltipViewParams(
        var tooltipBackgroundColor: Int = Color.WHITE,
        var tooltipTextGravity: Int = Gravity.CENTER,
        var tooltipTextAppearanceStyle: Int = R.style.TooltipDefaultStyle,
        var tooltipCornerRadius: Int = CORNER_RADIUS
)