package com.example.practiseapp.presentation.main

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.practiseapp.databinding.ProfilePageBinding
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.presentation.StartActivity
import dagger.hilt.android.AndroidEntryPoint

private const val GALLERY_REQUEST = 1

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: ProfilePageBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfilePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        binding.btnLogout.setOnClickListener {
            mainViewModel.signOut()
        }
        binding.btnDeleteToken.setOnClickListener {
            deleteTokenAndGoToStart()
        }
        binding.btnImage.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }
        mainViewModel.getUser()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode == RESULT_OK) {
                binding.btnImage.setImageURI(null)
                val selectedImage: Uri = data!!.data!!
                binding.btnImage.setImageURI(selectedImage)
                mainViewModel.saveImage(selectedImage.toString())
            }
        }
    }

    private fun deleteTokenAndGoToStart() {
        mainViewModel.deleteToken()
        StartActivity.start(requireActivity())
    }

    private fun subscribeObservers() {
        mainViewModel.logoutStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is Result.Success -> {
                    StartActivity.start(requireActivity())
                }
                is Result.Failure -> {
                    Toast.makeText(context, "$status code", Toast.LENGTH_LONG).show()
                }
            }
        }
        mainViewModel.userData.observe(viewLifecycleOwner) { userData ->
            when (userData) {
                is Result.Success -> {
                    binding.name.text = userData.data.firstName
                    binding.secondName.text = userData.data.lastName
                    if (userData.data.imageURI != null) {
                        binding.btnImage.setImageURI(null)
                        binding.btnImage.setImageURI(Uri.parse(userData.data.imageURI))
                    }
                }
                is Result.Failure -> {
                    Toast.makeText(context, "${userData.exception.message}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}
