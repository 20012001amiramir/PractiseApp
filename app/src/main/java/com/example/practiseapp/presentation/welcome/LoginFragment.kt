package com.example.practiseapp.presentation.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.practiseapp.databinding.LoginPageBinding
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.entities.AccountUser
import com.example.practiseapp.presentation.main.MainActivity
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
        welcomeViewModel.token.observe(viewLifecycleOwner) { consumable ->
            consumable.consume {
                when (it) {
                    is Result.Success -> {
                        MainActivity.start(requireActivity())
                    }
                    is Result.Failure -> {
                        val action = LoginFragmentDirections.actionLoginFragmentToFailedLoginFragment()
                        findNavController().navigate(action)
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
        fun newInstance() = LoginFragment()
    }
}
