package com.example.tooltip

import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.OvershootInterpolator

private const val ALPHA= "alpha"
private const val SCALE_X = "scaleX"
private const val SCALE_Y = "scaleY"
private const val VALUE_SHOW = 1f
private const val VALUE_HIDE = 0f

/**
 * Created by askhat on 21/11/18.
 */
class DefaultTooltipAnimator : TooltipAnimator {

    override fun popup(
            view: View,
            duration: Long,
            animatorListenerAdapter: AnimatorListenerAdapter
    ): ObjectAnimator {
        val popup = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat(ALPHA, VALUE_HIDE, VALUE_SHOW),
                PropertyValuesHolder.ofFloat(SCALE_X, VALUE_HIDE, VALUE_SHOW),
                PropertyValuesHolder.ofFloat(SCALE_Y, VALUE_HIDE, VALUE_SHOW)
        ).apply {
            this.duration = duration
            addListener(animatorListenerAdapter)
            interpolator = OvershootInterpolator()
        }

        return popup
    }

    override fun popout(
            view: View,
            duration: Long,
            animatorListenerAdapter: AnimatorListenerAdapter
    ): ObjectAnimator {
        val popout = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat(ALPHA, VALUE_SHOW, VALUE_HIDE),
                PropertyValuesHolder.ofFloat(SCALE_X, VALUE_SHOW, VALUE_HIDE),
                PropertyValuesHolder.ofFloat(SCALE_Y, VALUE_SHOW, VALUE_HIDE)
        ).apply {
            this.duration = duration
            addListener(animatorListenerAdapter)
            interpolator = AnticipateOvershootInterpolator()
        }

        return popout
    }
}