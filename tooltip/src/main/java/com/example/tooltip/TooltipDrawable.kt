package com.example.tooltip

import android.content.Context
import android.graphics.ColorFilter
import android.graphics.Rect
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat.getColor
import android.view.Gravity
import com.example.tooltip.extensions.dpToPx

/**
 * Created by askhat on 21/11/18.
 */
class TooltipDrawable(
        tooltip: Tooltip
) : Drawable() {

    private var boundTop: Int = 0
    private var boundLeft: Int = 0
    private var boundRight: Int = 0
    private var boundBottom: Int = 0
    private var boundCenter: Int = 0
    private var boundWidth: Int = 0
    private var arrowHeight: Int = dpToPx(tooltip.tooltipArrowParams.arrowHeight)
    private var arrowWidth: Int = dpToPx(tooltip.tooltipArrowParams.arrowWidth)
    private val tooltipBackgroundColor: Int = tooltip.tooltipViewParams.tooltipBackgroundColor
    private val tooltipAlign: Int = tooltip.tooltipOffsetParams.align
    private var cornerRadius: Int = dpToPx(tooltip.tooltipViewParams.tooltipCornerRadius)
    private val anchorViewWidth: Int = tooltip.anchorView.width
    private var centerXOffset: Float = 0f
    private var tooltipPaint: Paint = Paint()
    private val tooltipPadding: TooltipPaddingParams = tooltip.tooltipPadding
    private val shadowParams: TooltipShadowParams = tooltip.shadowParams
    private val shadowColor: Int = getShadowColor(tooltip.context, shadowParams.shadowColorId)
    private val tooltipPath = Path()
    private val tooltipPosition: TooltipPosition = tooltip.tooltipPosition

    init {
        initTooltipPaint()
    }

    override fun draw(
            canvas: Canvas
    ) {
        canvas.drawPath(getTooltipPath(), tooltipPaint)
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    override fun setAlpha(alpha: Int) = Unit

    override fun setColorFilter(cf: ColorFilter?) = Unit

    override fun getPadding(
            padding: Rect
    ): Boolean {
        padding.top =
                if (tooltipPosition == TooltipPosition.BOTTOM) dpToPx(tooltipPadding.top) + arrowHeight
                else dpToPx(tooltipPadding.top)
        padding.bottom =
                if (tooltipPosition == TooltipPosition.TOP) dpToPx(tooltipPadding.bottom) + arrowHeight
                else dpToPx(tooltipPadding.bottom)
        padding.right =
                if (tooltipPosition == TooltipPosition.LEFT) dpToPx(tooltipPadding.right) + arrowHeight
                else dpToPx(tooltipPadding.right)
        padding.left =
                if (tooltipPosition == TooltipPosition.RIGHT) dpToPx(tooltipPadding.left) + arrowHeight
                else dpToPx(tooltipPadding.left)

        return true
    }

    override fun onBoundsChange(
            bounds: Rect
    ) {
        super.onBoundsChange(bounds)

        boundWidth = bounds.width()
        boundTop = dpToPx(shadowParams.shadowPadding)
        boundBottom = bounds.height() - dpToPx(shadowParams.shadowPadding) * 2
        boundCenter = bounds.centerX()
        boundLeft = dpToPx(shadowParams.shadowPadding)
        boundRight = bounds.width() - dpToPx(shadowParams.shadowPadding) * 2
        centerXOffset = getCenterXOffset(tooltipAlign)
    }

    private fun initTooltipPaint() {
        tooltipPaint.isAntiAlias = true
        tooltipPaint.color = tooltipBackgroundColor
        tooltipPaint.style = Paint.Style.FILL
        if (shadowParams.shouldShowShadow) {
            tooltipPaint.setShadowLayer(
                    (shadowParams.shadowRadius).toFloat(),
                    dpToPx(shadowParams.shadowOffsetX).toFloat(),
                    dpToPx(shadowParams.shadowOffsetY).toFloat(),
                    shadowColor)
        }
    }

    private fun getTooltipPath(): Path {
        cornerRadius = if (cornerRadius < 0) 0 else cornerRadius
        arrowHeight = if (arrowHeight < 0) 0 else arrowHeight
        arrowWidth = if (arrowWidth < 0) 0 else arrowWidth

        val spacingLeft = (if (tooltipPosition == TooltipPosition.RIGHT) arrowHeight else 0).toFloat()
        val spacingTop = (if (tooltipPosition == TooltipPosition.BOTTOM) arrowHeight else 0).toFloat()
        val spacingRight = (if (tooltipPosition == TooltipPosition.LEFT) arrowHeight else 0).toFloat()
        val spacingBottom = (if (tooltipPosition == TooltipPosition.TOP) arrowHeight else 0).toFloat()

        val left = spacingLeft + boundLeft
        val top = spacingTop + boundTop
        val right = boundRight - spacingRight
        val bottom = boundBottom - spacingBottom
        val centerX = boundCenter + centerXOffset

        tooltipPath.moveTo(left + cornerRadius / 2, top)

        if (tooltipPosition == TooltipPosition.BOTTOM) {
            tooltipPath.lineTo(centerX - arrowWidth, top)
            tooltipPath.lineTo(centerX, boundTop.toFloat())
            tooltipPath.lineTo(centerX + arrowWidth, top)
        }
        tooltipPath.lineTo(right - cornerRadius / 2, top)
        tooltipPath.quadTo(right, top, right, top + cornerRadius / 2)

        if (tooltipPosition == TooltipPosition.LEFT) {
            tooltipPath.lineTo(right, bottom / 2 - arrowWidth)
            tooltipPath.lineTo(boundRight.toFloat(), bottom / 2)
            tooltipPath.lineTo(right, bottom / 2 + arrowWidth)
        }
        tooltipPath.lineTo(right, bottom - cornerRadius / 2)
        tooltipPath.quadTo(right, bottom, right - cornerRadius / 2, bottom)

        if (tooltipPosition == TooltipPosition.TOP) {
            tooltipPath.lineTo(centerX + arrowWidth, bottom)
            tooltipPath.lineTo(centerX, boundBottom.toFloat())
            tooltipPath.lineTo(centerX - arrowWidth, bottom)
        }
        tooltipPath.lineTo(left + cornerRadius / 2, bottom)
        tooltipPath.quadTo(left, bottom, left, bottom - cornerRadius / 2)

        if (tooltipPosition == TooltipPosition.RIGHT) {
            tooltipPath.lineTo(left, bottom / 2 + arrowWidth)
            tooltipPath.lineTo(boundLeft.toFloat(), bottom / 2)
            tooltipPath.lineTo(left, bottom / 2 - arrowWidth)
        }
        tooltipPath.lineTo(left, top + cornerRadius / 2)
        tooltipPath.quadTo(left, top, left + cornerRadius / 2, top)
        tooltipPath.close()

        return tooltipPath
    }

    private fun getCenterXOffset(
            tooltipAlign: Int
    ): Float = when (tooltipAlign) {
            Gravity.CENTER -> 0f
            Gravity.LEFT -> (anchorViewWidth - boundWidth) / 2f
            Gravity.RIGHT -> -(anchorViewWidth - boundWidth) / 2f
            else -> 0f
        }

    private fun getShadowColor(
            context: Context,
            colorId: Int
    ): Int = getColor(context, colorId)
}