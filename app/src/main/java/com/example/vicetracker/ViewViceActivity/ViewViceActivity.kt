package com.example.vicetracker.ViewViceActivity

import android.os.Bundle
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


class ViewViceActivity : AppCompatActivity() {
    //This is our viewModel instance for the MainActivity class
    private val viewViceViewModel: ViewViceViewModel by viewModels {
        ViewViceViewModelFactory((application as VicesApplication).repository, -1)
    }

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
        barChart = findViewById(R.id.chart)

        // Get ID from intent
        val id = intent.getIntExtra(EXTRA_ID,-1)
        viewViceViewModel.updateId(id)
        viewViceViewModel.curVice.observe(this) {
                vice->vice?.let { if (toolbar != null) { toolbar.setTitle(vice.name) } }
        }

        // format chart
        val xAxis: XAxis = barChart.getXAxis()
        val yAxis: YAxis = barChart.getAxisLeft()

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        xAxis.setValueFormatter(XAxisValueFormatter())

        // put data on chart
        // Create list of entry objects with data and X position
        val data = listOf(1,2,3,4,5,6,7)
        val dataEntries = mutableListOf<BarEntry>()
        for (i in data.indices) {
            dataEntries.add(BarEntry(i.toFloat(), data[i].toFloat()))
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