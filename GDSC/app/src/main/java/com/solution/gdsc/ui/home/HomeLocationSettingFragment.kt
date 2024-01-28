package com.solution.gdsc.ui.home

import android.content.ContentValues
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentHomeLocationSettingBinding

class HomeLocationSettingFragment :
    BaseFragment<FragmentHomeLocationSettingBinding>(R.layout.fragment_home_location_setting) {

    private lateinit var map: GoogleMap

    override fun setLayout() {
        onPlace()
        binding.btnLocationSettingNext.setOnClickListener {
            val action =
                HomeLocationSettingFragmentDirections.actionHomeLocationSettingToHomeRepairApply()
            findNavController().navigate(action)
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
                Log.i(ContentValues.TAG, "Place: ${place.name}, ${place.id}, ${place.address}")
            }

            override fun onError(status: Status) {
                Log.i(ContentValues.TAG, "An error occurred: $status")
            }
        })
    }

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

    companion object {
        private const val DEFAULT_ZOOM_LEVEL = 15.0f
    }

}