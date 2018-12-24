package com.example.tooltip

import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View

/**
 * Created by askhat on 21/11/18.
 */
interface TooltipAnimator {

    fun popup(view: View, duration: Long, animatorListenerAdapter: AnimatorListenerAdapter): ObjectAnimator

    fun popout(view: View, duration: Long, animatorListenerAdapter: AnimatorListenerAdapter): ObjectAnimator
}