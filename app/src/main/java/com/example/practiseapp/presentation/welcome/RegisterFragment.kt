package com.example.practiseapp.presentation.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        subscribeObservers()
        binding.btnRegister.setOnClickListener {
            val registerData = AccountUser(email = binding.etEmail.text.toString(),
                username = binding.etUsername.text.toString(),
                password = binding.etPassword.text.toString(),
                firstName = binding.etFirstName.text.toString(),
                lastName = binding.etSecondName.text.toString()
            )
            welcomeViewModel.signUp(registerData)
        }
    }

    private fun subscribeObservers() {
        welcomeViewModel.userData.observe(viewLifecycleOwner) { consumable ->
            consumable.consume {
                when (it) {
                    is Result.Success -> {
                        Toast.makeText(context, it.data.toString(), Toast.LENGTH_LONG).show()
                    }
                    is Result.Failure -> {
                        Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
                    }
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
