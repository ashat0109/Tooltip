package com.example.tooltip

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup

/**
 * Created by askhat on 21/11/18.
 */
private const val TOOLTIP_ANIMATION_DURATION = 300
private const val DEFAULT_RESOURCE_ID = 0

class Tooltip(
        builder: Builder
) {
    val context: Context = builder.context
    val anchorView: View = builder.anchorView
    val tooltipText: CharSequence = builder.tooltipText
    val tooltipPosition: TooltipPosition = builder.tooltipPosition
    val tooltipPadding: TooltipPaddingParams = builder.tooltipPadding
    val shadowParams: TooltipShadowParams = builder.shadowParams
    val tooltipOffsetParams: TooltipOffsetParams = builder.tooltipOffsetParams
    val tooltipViewParams: TooltipViewParams = builder.tooltipViewParams
    val tooltipArrowParams: TooltipArrowParams = builder.tooltipArrowParams
    val tooltipAnimator: TooltipAnimator = builder.tooltipAnimator
    val tooltipAnimationDuration: Int = builder.tooltipAnimationDuration
    val customViewResourceId: Int = builder.customViewResourceId
    val rootView: ViewGroup = builder.rootView

    class Builder(
            val context: Context,
            val anchorView: View,
            val tooltipText: CharSequence,
            var tooltipPosition: TooltipPosition
    ) {
        var tooltipPadding: TooltipPaddingParams = TooltipPaddingParams()
        var shadowParams: TooltipShadowParams = TooltipShadowParams()
        var tooltipOffsetParams: TooltipOffsetParams = TooltipOffsetParams()
        var tooltipViewParams: TooltipViewParams = TooltipViewParams()
        var tooltipArrowParams: TooltipArrowParams = TooltipArrowParams()
        var customViewResourceId: Int = DEFAULT_RESOURCE_ID
        var tooltipAnimationDuration: Int = TOOLTIP_ANIMATION_DURATION
        var tooltipAnimator: TooltipAnimator = DefaultTooltipAnimator()
        var rootView: ViewGroup = (context as Activity).window.decorView as ViewGroup

        fun build(): Tooltip {

            return Tooltip(this)
        }
    }
}