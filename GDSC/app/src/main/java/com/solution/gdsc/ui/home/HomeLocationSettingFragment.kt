package com.solution.gdsc.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentHomeLocationSettingBinding
import com.solution.gdsc.util.PermissionUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeLocationSettingFragment :
    BaseFragment<FragmentHomeLocationSettingBinding>(R.layout.fragment_home_location_setting),
    OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, ActivityCompat.OnRequestPermissionsResultCallback {
    private var permissionDenied = false
    private lateinit var map: GoogleMap
    private val args: HomeLocationSettingFragmentArgs by navArgs()
    private var placeId: String? = null
    private var address: String? = null
    private var district: String? = null

    override fun setLayout() {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.container_map_home) as SupportMapFragment
        mapFragment.getMapAsync(this)
        onPlace()
        binding.btnLocationSettingNext.setOnClickListener {
            if (checkPlace()) {
                val action =
                    HomeLocationSettingFragmentDirections.actionHomeLocationSettingToHomeRepairApply(
                        args.image, placeId!!, address!!, district!!
                    )
                findNavController().navigate(action)
            }
        }
        binding.toolbarLocationSetting.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun onPlace() {
        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragmentHome =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment_home)
                    as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragmentHome.setPlaceFields(
            listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
        )

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragmentHome.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                moveMapToLocation(place.latLng)
                placeId = place.id
                address = place.name
                district = place.address!!
                    .replace(address!!, "")
                    .replace(Regex("경기도|경상남도|경상북도|전라남도|전라북도|강원도|충청북도|충청남도"), "")
                    .trim()
                    .replace("\\s+".toRegex(), " ")
            }

            override fun onError(status: Status) {
                Log.e(ContentValues.TAG, "An error occurred: $status")
            }
        })
    }

    private fun checkPlace() = !placeId.isNullOrEmpty() && !address.isNullOrEmpty() && !district.isNullOrEmpty()

    private fun moveMapToLocation(location: LatLng?) {
        if (location != null) {
            // Assuming you have a GoogleMap instance, you can move the camera to the selected location
            // mMap is the instance of GoogleMap
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    location,
                    DEFAULT_ZOOM_LEVEL
                )
            )
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission not granted yet, request it
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission granted, enable the my location layer
            enableMyLocation()
        }
        googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(37.5642135, 127.0016985))
                .title("Marker")
        )
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        // Check if permissions are granted
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Enable the my location layer
            map.isMyLocationEnabled = true

            // Get last known location and move the camera
            val fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(requireActivity())
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        val latLng = LatLng(location.latitude, location.longitude)
                        map.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                latLng,
                                DEFAULT_ZOOM_LEVEL
                            )
                        )
                    }
                }
        }
    }

    private fun showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog.newInstance(true)
            .show(childFragmentManager, "dialog")
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(requireActivity(), "MyLocation button clicked", Toast.LENGTH_SHORT)
            .show()
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(requireActivity(), "Current location:\n$location", Toast.LENGTH_LONG)
            .show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
            return
        }

        if (PermissionUtils.isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) || PermissionUtils.isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation()
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true
        }
    }

    override fun onResume() {
        super.onResume()
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError()
            permissionDenied = false
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val DEFAULT_ZOOM_LEVEL = 15.0f
    }
}