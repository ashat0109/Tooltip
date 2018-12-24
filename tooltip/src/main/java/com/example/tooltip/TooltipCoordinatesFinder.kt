package com.example.tooltip

import android.graphics.Point
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.example.tooltip.extensions.dpToPx

/**
 * Created by askhat on 21/11/18.
 */
class TooltipCoordinatesFinder {

    /**
     * Возвращает координаты подсказки
     *
     * @param tooltipView - вью подсказки
     * @param tooltip - объект подсказки
     * @return point
     */
    fun getTooltipCoordinates(
            tooltipView: View,
            tooltip: Tooltip
    ): Point {
        val anchorViewCoordinates = Coordinates(tooltip.anchorView)
        val rootCoordinates = Coordinates(tooltip.rootView)
        tooltipView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val point = when (tooltip.tooltipPosition) {
            TooltipPosition.TOP -> getPositionAbove(
                    tooltipView,
                    tooltip,
                    anchorViewCoordinates,
                    rootCoordinates)
            TooltipPosition.BOTTOM -> getPositionBelow(
                    tooltipView,
                    tooltip,
                    anchorViewCoordinates,
                    rootCoordinates)
            TooltipPosition.LEFT -> getPositionLeft(
                    tooltipView,
                    tooltip,
                    anchorViewCoordinates,
                    rootCoordinates)
            TooltipPosition.RIGHT -> getPositionRight(
                    tooltipView,
                    tooltip,
                    anchorViewCoordinates,
                    rootCoordinates)
        }

        point.x += dpToPx(tooltip.tooltipOffsetParams.offsetX)
        point.y += dpToPx(tooltip.tooltipOffsetParams.offsetY)

        return point
    }

    private fun getPositionRight(
            tipView: View,
            tooltip: Tooltip,
            anchorViewCoordinates: Coordinates,
            rootLocation: Coordinates
    ): Point {
        val point = Point()
        point.x = anchorViewCoordinates.right
        adjustRightOutOfBounds(
                tipView,
                point,
                rootLocation.right - anchorViewCoordinates.right,
                rootLocation)
        point.y = anchorViewCoordinates.top + getYCenteringOffset(tipView, tooltip)
        return point
    }

    private fun getPositionLeft(
            tipView: View,
            tooltip: Tooltip,
            anchorViewCoordinates: Coordinates,
            rootLocation: Coordinates
    ): Point {
        val point = Point()
        point.x = anchorViewCoordinates.left - tipView.measuredWidth
        adjustLeftOutOfBounds(
                tipView,
                point,
                anchorViewCoordinates.left - rootLocation.left, rootLocation)
        point.y = anchorViewCoordinates.top + getYCenteringOffset(tipView, tooltip)
        return point
    }

    private fun getPositionBelow(
            tipView: View,
            tooltip: Tooltip,
            anchorViewCoordinates: Coordinates,
            rootLocation: Coordinates
    ): Point {
        val point = Point()
        point.x = anchorViewCoordinates.left + getXOffset(tipView, tooltip)
        adjustTooltipAlignment(
                tooltip,
                tipView,
                point,
                anchorViewCoordinates,
                rootLocation)
        point.y = anchorViewCoordinates.bottom
        return point
    }

    private fun getPositionAbove(
            tipView: View,
            tooltip: Tooltip,
            anchorViewCoordinates: Coordinates,
            rootLocation: Coordinates
    ): Point {
        val point = Point()
        point.x = anchorViewCoordinates.left + getXOffset(tipView, tooltip)
        adjustTooltipAlignment(tooltip, tipView, point, anchorViewCoordinates, rootLocation)
        point.y = anchorViewCoordinates.top - tipView.measuredHeight
        return point
    }

    private fun adjustTooltipAlignment(
            tooltip: Tooltip,
            tipView: View,
            point: Point,
            anchorViewCoordinates: Coordinates,
            rootLocation: Coordinates) {
        when (tooltip.tooltipOffsetParams.align) {
            Gravity.CENTER -> adjustCenterOutOfBounds(
                    tipView,
                    point,
                    rootLocation)
            Gravity.LEFT -> adjustRightOutOfBounds(
                    tipView,
                    point,
                    rootLocation.right - anchorViewCoordinates.left,
                    rootLocation)
            Gravity.RIGHT -> adjustLeftOutOfBounds(
                    tipView,
                    point,
                    anchorViewCoordinates.right - rootLocation.left,
                    rootLocation)
            else -> Unit
        }
    }

    private fun adjustRightOutOfBounds(
            tipView: View,
            point: Point,
            tooltipWidth: Int,
            rootLocation: Coordinates
    ) {
        if (point.x + tipView.measuredWidth > rootLocation.right) {
            val params = tipView.layoutParams
            params.width = tooltipWidth
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            tipView.layoutParams = params
            measureViewWithFixedWidth(tipView, params.width)
        }
    }

    private fun adjustLeftOutOfBounds(
            tipView: View,
            point: Point,
            tooltipWidth: Int,
            rootLocation: Coordinates
    ) {
        if (point.x < rootLocation.left) {
            val params = tipView.layoutParams
            point.x = rootLocation.left
            params.width = tooltipWidth
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            tipView.layoutParams = params
            measureViewWithFixedWidth(tipView, params.width)
        }
    }

    private fun adjustCenterOutOfBounds(
            tipView: View,
            point: Point,
            rootLocation: Coordinates
    ) {
        adjustLeftOutOfBounds(tipView, point, ViewGroup.LayoutParams.WRAP_CONTENT, rootLocation)
        if (point.x + tipView.measuredWidth > rootLocation.right) {
            val params = tipView.layoutParams
            point.x = rootLocation.right - tipView.measuredWidth
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            tipView.layoutParams = params
            measureViewWithFixedWidth(tipView, params.width)
        }
    }

    private fun measureViewWithFixedWidth(
            tipView: View,
            width: Int
    ) {
        tipView.measure(
                View.MeasureSpec.makeMeasureSpec(
                        width,
                        View.MeasureSpec.EXACTLY),
                ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun getXOffset(
            tipView: View,
            toolTip: Tooltip
    ): Int = when (toolTip.tooltipOffsetParams.align) {
        Gravity.CENTER -> (toolTip.anchorView.width - tipView.measuredWidth) / 2
        Gravity.LEFT -> 0
        Gravity.RIGHT -> toolTip.anchorView.width - tipView.measuredWidth
        else -> 0
    }

    private fun getYCenteringOffset(
            tipView: View,
            toolTip: Tooltip
    ): Int = (toolTip.anchorView.height - tipView.measuredHeight) / 2
}