package com.example.practiseapp.presentation.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.practiseapp.R
import com.example.practiseapp.databinding.RegisterPageBinding
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.entities.AccountUser
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: RegisterPageBinding? = null
    private val binding get() = _binding!!
    private val welcomeViewModel: WelcomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RegisterPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener {
            val registerData = AccountUser(email = binding.etEmail.toString(),
                username = binding.etUsername.toString(),
                password = binding.etPassword.toString(),
                firstName = binding.etFirstName.toString(),
                lastName = binding.etSecondName.toString()
            )
            welcomeViewModel.signUp(registerData)
        }
    }

    private fun subscribeObservers() {
        welcomeViewModel.isAuthenticated.observe(viewLifecycleOwner) { isAuthenticated ->
            when (isAuthenticated) {
                is Result.Success -> {
                    Snackbar.make(
                        requireActivity().findViewById(R.id.root_welcome),
                        isAuthenticated.data.toString(),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is Result.Failure -> {
                    Snackbar.make(
                        requireActivity().findViewById(R.id.root_welcome),
                        isAuthenticated.exception.message ?: "Has not been signed up",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegisterFragment()
    }
}
