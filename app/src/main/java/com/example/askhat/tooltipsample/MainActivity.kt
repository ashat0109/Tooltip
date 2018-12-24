package com.example.askhat.tooltipsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.example.tooltip.Tooltip
import com.example.tooltip.TooltipManager
import com.example.tooltip.TooltipPosition

class MainActivity : AppCompatActivity() {

    private var tooltip: Tooltip? = null
    private val tooltipManager: TooltipManager = TooltipManager(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTooltip()
    }

    private fun initTooltip() {
        val textView = findViewById<TextView>(R.id.textView)
        val builder = Tooltip.Builder(
                context = this,
                anchorView = textView,
                tooltipText = "Text",
                tooltipPosition = TooltipPosition.BOTTOM)
        tooltip = builder.build()
        textView.setOnClickListener({ tooltipManager.showTooltip(tooltip) })
    }
}