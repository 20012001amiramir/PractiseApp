package com.example.practiseapp.presentation.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.practiseapp.databinding.LoginFailedBinding

class FailedLoginFragment : Fragment() {

    private var _binding: LoginFailedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFailedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRetryLogin.setOnClickListener {
            val action = FailedLoginFragmentDirections.actionFailedLoginFragmentToLoginFragment()
            it.findNavController().navigate(action)
        }
        binding.btnRegister.setOnClickListener {
            val action = FailedLoginFragmentDirections.actionFailedLoginFragmentToRegisterFragment()
            it.findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = FailedLoginFragment()
    }
}