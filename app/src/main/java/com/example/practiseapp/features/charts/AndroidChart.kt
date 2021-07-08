package com.example.practiseapp.features.charts

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class AndroidChart {
    private lateinit var temperatures: ArrayList<Entry>
    private lateinit var lineChart: LineChart
    private lateinit var dataSets: ArrayList<ILineDataSet>
    private lateinit var lineDataSet : LineDataSet
    public fun initChart(array : ArrayList<Entry>, lineChart: LineChart){
        lineDataSet = LineDataSet(array,"Temperature")
        lineDataSet.color = Color.Red.toArgb()
        lineChart.setBackgroundColor(Color.White.toArgb())
        lineChart.axisLeft.setDrawAxisLine(false)
        dataSets = arrayListOf()
        dataSets.add(lineDataSet)
        val data = LineData(dataSets)
        lineChart.animateX(1000)
        lineChart.data = data
    }
    public fun setNewData(entry: Entry){
        
    }
}
