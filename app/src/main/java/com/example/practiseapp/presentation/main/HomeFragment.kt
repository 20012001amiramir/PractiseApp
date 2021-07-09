package com.example.practiseapp.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.practiseapp.databinding.HomePageBinding
import com.example.practiseapp.features.bluetooth.BluetoothServer
import com.example.practiseapp.features.charts.AndroidChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry

class HomeFragment : Fragment() {

    private var _binding: HomePageBinding? = null
    private val binding get() = _binding!!
    private var androidChart: AndroidChart = AndroidChart()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomePageBinding.inflate(inflater, container, false)
        binding.connectToDevice.setOnClickListener{
            BluetoothServer(requireActivity().application, binding).connectBluetooth()
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
