package com.example.practiseapp.presentation.main

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.practiseapp.Constants
import com.example.practiseapp.databinding.ProfilePageBinding
import com.example.practiseapp.domain.common.Result
import com.example.practiseapp.domain.utils.FileUtil
import com.example.practiseapp.presentation.StartActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

private const val GALLERY_REQUEST = 1
private const val REQUEST_EXTERNAL_STORAGE = 1
private val PERMISSIONS_STORAGE = arrayOf(
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: ProfilePageBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    @ApplicationContext
    @Inject
    lateinit var appContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfilePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val permission = ActivityCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }

        subscribeObservers()

        binding.btnLogout.setOnClickListener {
            mainViewModel.signOut()
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
                val selectedImageUri: Uri = data!!.data!!
                val path = FileUtil.getPath(selectedImageUri, requireContext())
                binding.btnImage.setImageURI(selectedImageUri)
                if (path != null) {
                    mainViewModel.saveImage(path)
                } else {
                    Toast.makeText(context, "Path of selected image is null", Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    private fun subscribeObservers() {
        mainViewModel.logoutStatus.observe(viewLifecycleOwner) { consumable ->
            consumable.consume {
                when (it) {
                    is Result.Success -> {
                        StartActivity.start(requireActivity())
                    }
                    is Result.Failure -> {
                        Toast.makeText(context, "${it.exception.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
        mainViewModel.userData.observe(viewLifecycleOwner) { userData ->
            when (userData) {
                is Result.Success -> {
                    binding.name.text = userData.data.firstName
                    binding.secondName.text = userData.data.lastName
                    val imageUri = userData.data.imageURI
                    if (imageUri != null) {
                        val photoURI = FileProvider.getUriForFile(appContext, Constants.APPLICATION_ID + ".provider", File(imageUri))
                        binding.btnImage.setImageURI(photoURI)
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
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}
