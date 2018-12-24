package com.example.tooltip

import android.support.annotation.IdRes
import android.view.View

/**
 * Created by askhat on 21/11/18.
 */
interface TooltipListener {

    fun onTooltipShown(
            view: View,
            @IdRes
            anchorViewId: Int)

    fun onTooltipDismissed(
            view: View,
            @IdRes
            anchorViewId: Int)
}