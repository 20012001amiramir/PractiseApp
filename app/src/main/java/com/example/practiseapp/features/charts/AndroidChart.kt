package com.example.practiseapp.features.charts

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class AndroidChart {
    private var _temperatures: ArrayList<Entry> = arrayListOf()
    private var _dataSets: ArrayList<ILineDataSet> = arrayListOf()
    private lateinit var _lineChart: LineChart
    private lateinit var _lineDataSet : LineDataSet
    fun initChart(array : ArrayList<Entry>, lineChart: LineChart){
        _lineChart = lineChart
        _temperatures = array
        _lineDataSet = LineDataSet(_temperatures,"Temperature")
        _lineDataSet.color = Color.Red.toArgb()
        _lineChart.setBackgroundColor(Color.White.toArgb())
        _lineChart.axisLeft.setDrawAxisLine(false)
        _dataSets.add(0,_lineDataSet)
        val data = LineData(_dataSets)
        _lineChart.data = data
    }
    fun updateChart(entry: Entry){
        _lineChart.lineData.addEntry(entry,0)
        _lineChart.notifyDataSetChanged()
        _lineChart.setBackgroundColor(Color.White.toArgb())
    }
}
