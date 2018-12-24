package com.example.tooltip.extensions

import android.content.res.Resources

/**
 * Created by askhat on 24/12/18.
 */
fun dpToPx(
        dp: Int
): Int = (dp * Resources.getSystem().displayMetrics.density).toInt()