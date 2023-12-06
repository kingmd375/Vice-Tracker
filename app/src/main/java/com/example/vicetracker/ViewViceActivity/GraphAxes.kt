package com.example.vicetracker.ViewViceActivity

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.Utils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.roundToInt

// classes that determine how the chart axes look
class XAxisValueFormatter() : ValueFormatter() {
    private val formatter = SimpleDateFormat("MMM-DD")
    override fun getAxisLabel(value: Float, axis: AxisBase): String {

        var position = value.roundToInt()

        // get the date for the given position
        val currentDate = Date().time
        val positionDate = currentDate - 1000*3600*24 * (7 - position)
        return formatter.format(positionDate)
    }
}

class YAxisValueFormatter : ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase): String {
        return value.toString() + "k"
    }
}