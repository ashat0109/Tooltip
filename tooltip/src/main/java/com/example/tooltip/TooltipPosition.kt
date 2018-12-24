package com.example.tooltip

/**
 * Created by askhat on 21/11/18.
 */
sealed class TooltipPosition {
    object LEFT : TooltipPosition()
    object RIGHT : TooltipPosition()
    object TOP : TooltipPosition()
    object BOTTOM : TooltipPosition()
}