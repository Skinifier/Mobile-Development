package com.capstone.skinifier.view.scan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.capstone.skinifier.data.pref.PredictData
import com.capstone.skinifier.data.response.PredictResponse
import com.capstone.skinifier.databinding.FragmentScanFaceBinding
import com.capstone.skinifier.view.camera.CameraActivity
import com.capstone.skinifier.view.camera.CameraActivity.Companion.CAMERAX_RESULT
import com.capstone.skinifier.view.camera.reduceFileImage
import com.capstone.skinifier.view.camera.uriToFile
import com.capstone.skinifier.view.result.ResultFragment
import com.capstone.skinifier.view.viewModelFactory.MLViewModelFactory
import java.io.File

class ScanFaceFragment : Fragment() {
    private val scanFaceViewModel: ScanFaceViewModel by viewModels {
        MLViewModelFactory.getInstance(requireContext())
    }

    private var _binding: FragmentScanFaceBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null
    private var photoFile: File? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScanFaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.kamera.setOnClickListener {
            if (allPermissionsGranted()) {
                startCameraX()
            } else {
                requestPermissionLauncher.launch(REQUIRED_PERMISSION)
            }
        }

        binding.gallery.setOnClickListener {
            startGallery()
        }

        binding.buttonSubmit.setOnClickListener {
            submitImage()
        }

        scanFaceViewModel.predictionResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response != null && response.message == "Prediction Success") {
                handleResponse(response)
            }
        })
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            photoFile = uriToFile(uri, requireContext()).reduceFileImage()
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCameraX() {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            photoFile = currentImageUri?.let { uri -> uriToFile(uri, requireContext()).reduceFileImage() }
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.photo.setImageURI(it)
        }
    }

    private fun submitImage() {
        val predictData = PredictData(
            imagefile = currentImageUri?.let { uriToFile(it, requireContext()).reduceFileImage() }
        )

        scanFaceViewModel.submitImage(predictData)
    }

    private fun handleResponse(response: PredictResponse) {
        val skinCondition = response.prediction.prediction
        val fragment = ResultFragment.newInstance(skinCondition)
        fragment.show(parentFragmentManager, fragment.tag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }



}