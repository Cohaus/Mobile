package com.solution.gdsc.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home),
    ActivityCompat.OnRequestPermissionsResultCallback {

    override fun setLayout() {
        requestLocationPermission()
        requestCameraPermission()
        setClickListener()
    }

    private fun setClickListener() {
        with(binding) {
            btnAiDiagnosis.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeToHomeAiCategory()
                findNavController().navigate(action)
            }
            btnBasicCapture.setOnClickListener {
                //dispatchTakePictureIntent()
                val action = HomeFragmentDirections.actionHomeToCamera(null)
                findNavController().navigate(action)
            }
            btnUseGuide.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeToHomeUseGuid()
                findNavController().navigate(action)
            }
        }
    }

    private fun requestLocationPermission() {
        // Check if the location permission is granted
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    private fun requestCameraPermission() {
        // Check if the camera permission is granted
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return
        } else {
            // Request camera permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Location permission granted, perform your location-related task
                } else {
                    // Location permission denied, show a message or handle accordingly
                    Log.d("LocationPermission", "Location permission denied")
                }
                return
            }
            else -> {
                // Handle other permissions if needed
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 101
        private const val REQUEST_LOCATION_PERMISSION = 102

    }
}