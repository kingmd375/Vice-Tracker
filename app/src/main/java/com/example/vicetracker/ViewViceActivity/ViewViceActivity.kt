package com.example.vicetracker.ViewViceActivity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.vicetracker.NewViceActivity.EXTRA_ID
import com.example.vicetracker.R
import com.example.vicetracker.VicesApplication
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import java.util.Date


class ViewViceActivity : AppCompatActivity() {
    //This is our viewModel instance for the MainActivity class
    private val viewViceViewModel: ViewViceViewModel by viewModels {
        ViewViceViewModelFactory((application as VicesApplication).repository, -1)
    }

    lateinit var engagementView: TextView
    lateinit var barChart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_vice)
        setSupportActionBar(findViewById(R.id.toolbar))

        // get UI components
        val toolbar = getSupportActionBar()
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true)
        }
        engagementView = findViewById(R.id.engagementView)
        barChart = findViewById(R.id.chart)

        // format chart
        formatChart()

        // Get ID from intent and get that vice's data
        val id = intent.getIntExtra(EXTRA_ID,-1)
        viewViceViewModel.updateId(id)
        viewViceViewModel.curVice.observe(this) {
            vice->vice?.let {
                if (toolbar != null) { toolbar.setTitle(vice.name) }
                engagementView.text = "${viewViceViewModel.curVice.value?.amount} / ${viewViceViewModel.curVice.value?.limit} ${viewViceViewModel.curVice.value?.unit}"
            }
        }
        viewViceViewModel.curViceDayAmounts.observe(this) {
            if (toolbar != null) addChartData()
        }
    }

    private fun formatChart() {
        val xAxis: XAxis = barChart.getXAxis()
        val yAxis: YAxis = barChart.getAxisLeft()

        // format x axis
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        xAxis.setValueFormatter(XAxisValueFormatter())
        Log.d("ViewVice", "formatted chart")

        // get rid of legend
        barChart.legend.isEnabled = false
    }

    private fun addChartData() {
        Log.d("ViewVice", "adding chart data...")
        // Create list of entry objects with data and X position
        // get the day amounts of the current vice
        val allDayAmounts = viewViceViewModel.curViceDayAmounts.value

        // make a new list with an amount for each of the past 7 days
        // data that will go into chart
        val dataEntries = mutableListOf<BarEntry>()
        // today's date with time removed and date from a week ago
        val currentTime = Date().time
        val currentDate = currentTime - currentTime % (24*60*60*1000)
        for(i in 0..6) {
            // see if there is a dayAmount in allDayAmounts corresponding to this day, return its indexj
            Log.d("ViewVice", "day $i")
            var ithDay = (6-i)*24*60*60*1000
            var index = -1
            if (allDayAmounts != null) {
                for (j in allDayAmounts.indices) {
                    if (currentDate - ithDay == allDayAmounts[j].date) {
                        index = j
                        break
                    }
                }
            } else Log.d("ViewVice", "allDayAmounts is null!!!")

            // if a day amount for this day was found, make an entry with it, make a blank entry otherwise
            if (index != -1 && allDayAmounts != null) {
                Log.d("ViewVice", "index $index found")
                dataEntries.add(BarEntry(i.toFloat(), allDayAmounts[index].amount.toFloat()))
            } else {
                Log.d("ViewVice", "index not found")
                dataEntries.add((BarEntry(i.toFloat(), 0.toFloat())))
            }
        }

        // give data lable and add to chart
        val dataSet = BarDataSet(dataEntries, "Daily Engagement")
        barChart.data = BarData(dataSet)
    }

    // override action bar back button function
    override fun onSupportNavigateUp(): Boolean {
        setResult(RESULT_OK)
        finish()
        return true
    }
}