package com.example.tooltip

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.tooltip.*

/**
 * Created by askhat on 21/11/18.
 */
class TooltipManager(
        private val tooltipListener: TooltipListener?
) {
    private lateinit var tooltipAnimator: TooltipAnimator
    private var tooltipAnimationDuration: Int = 0
    private var tooltipView: View? = null
    private val handler = Handler()

    fun showTooltip(
            toolTip: Tooltip?
    ) {
        toolTip ?: return

        handler.post({
            if (toolTip.anchorView.visibility != View.GONE) {
                tooltipView = initTooltipView(toolTip)
                showTooltipView(tooltipView)
            }
        })
    }

    fun hideTooltip(
            toolTip: Tooltip?
    ) {
        toolTip ?: return

        toolTip.rootView.removeView(tooltipView)
    }

    private fun initTooltipView(
            tooltip: Tooltip
    ): View {
        val tooltipView = createTooltipView(tooltip)
        setTooltipBackground(tooltipView, TooltipDrawable(tooltip))
        tooltip.rootView.addView(
                tooltipView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        val point = TooltipCoordinatesFinder().getTooltipCoordinates(tooltipView, tooltip)
        moveTooltipToPosition(tooltipView, point)
        tooltipView.setOnClickListener { dismissTooltip(tooltipView) }
        tooltipAnimator = tooltip.tooltipAnimator
        tooltipAnimationDuration = tooltip.tooltipAnimationDuration

        return tooltipView
    }

    private fun setTooltipBackground(
            view: View,
            drawable: Drawable
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            view.background = drawable
        } else {
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            view.setBackgroundDrawable(drawable)
        }
    }

    private fun moveTooltipToPosition(
            tooltipView: View,
            point: Point
    ) {
        val tooltipViewCoordinates = Coordinates(tooltipView)
        val translationX = point.x - tooltipViewCoordinates.left
        val translationY = point.y - tooltipViewCoordinates.top
        tooltipView.translationX = translationX.toFloat()
        tooltipView.translationY = translationY.toFloat()
    }

    private fun createTooltipView(
            toolTip: Tooltip
    ): View = if (toolTip.customViewResourceId != 0) {
        createCustomTooltipView(toolTip)
    } else createDefaultTooltipView(toolTip)

    private fun createDefaultTooltipView(
            tooltip: Tooltip
    ): View {
        val tipView = TextView(tooltip.context)
        tipView.text = tooltip.tooltipText
        tipView.gravity = tooltip.tooltipViewParams.tooltipTextGravity
        setTextAppearance(tipView, tooltip)

        return tipView
    }

    private fun createCustomTooltipView(
            tooltip: Tooltip
    ): View {
        val layoutInflater = tooltip.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        return layoutInflater.inflate(tooltip.customViewResourceId, null)
    }

    private fun setTextAppearance(
            tipView: TextView,
            toolTip: Tooltip
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tipView.setTextAppearance(toolTip.tooltipViewParams.tooltipTextAppearanceStyle)
        } else {
            tipView.setTextAppearance(toolTip.context, toolTip.tooltipViewParams.tooltipTextAppearanceStyle)
        }
    }

    private fun showTooltipView(
            tooltipView: View?
    ) {
        tooltipView ?: return

        animateTooltipShow(tooltipView)
    }

    private fun dismissTooltip(
            tooltipView: View?
    ) {
        tooltipView ?: return

        animateTooltipDismiss(tooltipView)
    }

    private fun animateTooltipDismiss(
            view: View
    ) {
        tooltipAnimator.popout(
                view,
                tooltipAnimationDuration.toLong(),
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(
                            animation: Animator
                    ) {
                        super.onAnimationEnd(animation)
                        tooltipListener?.onTooltipDismissed(view, view.id)
                    }
                }).start()
    }

    private fun animateTooltipShow(
            view: View
    ) {
        tooltipAnimator.popup(
                view,
                tooltipAnimationDuration.toLong(),
                object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(
                            animation: Animator
                    ) {
                        super.onAnimationStart(animation)
                        tooltipListener?.onTooltipShown(view, view.id)
                    }
                }).start()
    }
}