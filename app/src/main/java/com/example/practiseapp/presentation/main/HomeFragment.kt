package com.example.practiseapp.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.practiseapp.features.bluetooth.BluetoothServer
import com.example.practiseapp.databinding.HomePageBinding
import com.example.practiseapp.features.charts.AndroidChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    private var _binding: HomePageBinding? = null
    private val binding get() = _binding!!
    private var androidChart: AndroidChart = AndroidChart()
    private val homeViewModel: HomeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomePageBinding.inflate(inflater, container, false)
        binding.connectToDevice.setOnClickListener{

        }
        return binding.root

    }
    private fun initChartActivity(lineChart: LineChart){
        val array = arrayListOf<Entry>(Entry(0F, 0F))
        array.add(Entry(0F, 50F))
        array.add(Entry(1F, 50F))
        array.add(Entry(2F, 100F))
        array.add(Entry(3F, 0F))
        androidChart.initChart(array,lineChart)
    }
    override fun onStart() {
        super.onStart()

        initChartActivity(binding.anyChartView)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    companion object {
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
