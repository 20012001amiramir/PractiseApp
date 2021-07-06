package com.example.practiseapp.presentation.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.practiseapp.R
import com.example.practiseapp.databinding.LoginPageBinding
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.entities.AccountUser
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: LoginPageBinding? = null
    private val binding get() = _binding!!
    private val welcomeViewModel: WelcomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        binding.btnLogin.setOnClickListener {
            val loginData = AccountUser(username = binding.etUsername.text.toString(),
                password = binding.etPassword.text.toString())
            welcomeViewModel.signIn(loginData)
        }
    }

    private fun subscribeObservers() {
        welcomeViewModel.token.observe(viewLifecycleOwner) { token ->
            when (token) {
                is Result.Success -> {
                    Toast.makeText(context, token.data, Toast.LENGTH_LONG).show()
                }
                is Result.Failure -> {
                    Toast.makeText(context, token.exception.message, Toast.LENGTH_LONG).show()
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
        fun newInstance() = LoginFragment()
    }
}
