package com.solution.gdsc.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.location.Geocoder
import android.location.Location
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentMapBinding
import com.solution.gdsc.domain.model.response.CountRepairDto
import com.solution.gdsc.ui.map.adapter.RepairApplyRecordAdapter
import com.solution.gdsc.ui.map.viewmodel.MapViewModel
import com.solution.gdsc.ui.profile.adapter.RepairClickListener
import com.solution.gdsc.util.PermissionUtils.PermissionDeniedDialog.Companion.newInstance
import com.solution.gdsc.util.PermissionUtils.isPermissionGranted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map),
    OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, ActivityCompat.OnRequestPermissionsResultCallback,
    RepairClickListener {
    private var permissionDenied = false
    private lateinit var map: GoogleMap
    private lateinit var adapter: RepairApplyRecordAdapter
    private var selectedMarker: Marker? = null
    private val viewModel by viewModels<MapViewModel>()
    private lateinit var geocoder: Geocoder

    override fun setLayout() {
        geocoder = Geocoder(requireContext())
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.container_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        onPlace()
    }

    private fun onPlace() {
        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(
            listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
        )

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                moveMapToLocation(place.latLng)
                Log.i(ContentValues.TAG, "Place: ${place.name}, ${place.id}, ${place.address} ${place.latLng}")
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
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, DEFAULT_ZOOM_LEVEL))
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
        map.setOnMarkerClickListener {
            onMarkerClick(it)
            showRecyclerView()
            true
        }

        googleMap.setOnMapClickListener {
            // 다른 영역을 터치할 때 RecyclerView를 감춤
            hideRecyclerView()
        }
        viewModel.getAllRepairRecord()
        observe()
    }

    private fun addMarker(countRepair: CountRepairDto) {
        val lat = geocoder.getFromLocationName(countRepair.district, 10)
        val latitude: Double
        val longitude: Double
        if (!lat.isNullOrEmpty()) {
            latitude = lat[0].latitude
            longitude = lat[0].longitude
        } else {
            Log.e(TAG, "주소가 존재하지 않음")
            return
        }

        val markerLatLng = LatLng(latitude, longitude)

        val markerOptions = MarkerOptions()
            .position(markerLatLng)
            .title(countRepair.legalDistrict.toString())
            .icon(generateCustomMarkerBitmapDescriptor(countRepair.count, requireContext()))

        map.addMarker(markerOptions)
    }

    private fun onMarkerClick(marker: Marker) {
        viewModel.getRequestRepairList(marker.title!!.toLong())
        val repairAdapter = RepairApplyRecordAdapter(this)
        initRequestRepairAdapter(repairAdapter)
        binding.rvRepairRecordApply.adapter = repairAdapter
    }

    private fun initRequestRepairAdapter(repairAdapter: RepairApplyRecordAdapter) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.requestRepairList.collectLatest {
                    repairAdapter.update(it)
                }
            }
        }
    }

    private fun hideRecyclerView() {
        binding.rvRepairRecordApply.visibility = View.GONE
        selectedMarker = null
    }

    private fun showRecyclerView() {
        binding.rvRepairRecordApply.visibility = View.VISIBLE
    }

    private fun generateCustomMarkerBitmapDescriptor(count: Long, context: Context): BitmapDescriptor {
        val diameter = 120 // 마커의 지름
        val bitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()

        // 배경 (하얀색 원)
        paint.color = Color.WHITE
        canvas.drawCircle((diameter / 2).toFloat(), (diameter / 2).toFloat(), (diameter / 2).toFloat(), paint)

        // 테두리 (초록색 테두리)
        paint.color = ContextCompat.getColor(context, R.color.green_300)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 8f
        canvas.drawCircle((diameter / 2).toFloat(), (diameter / 2).toFloat(), (diameter / 2 - 5).toFloat(), paint)

        // 텍스트 (게시물 개수)
        paint.style = Paint.Style.FILL
        paint.color = Color.BLACK
        paint.textSize = 50f
        paint.textAlign = Paint.Align.CENTER

        // 폰트 설정
        paint.typeface = getCustomTypeface(context)

        // 텍스트의 baseline 계산
        val textBaseline = (canvas.height - paint.fontMetricsInt.bottom + paint.fontMetricsInt.top) / 2 - paint.fontMetricsInt.top

        canvas.drawText(count.toString(), (diameter / 2).toFloat(), textBaseline.toFloat(), paint)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun getCustomTypeface(context: Context): Typeface {
        return ResourcesCompat.getFont(context, R.font.noto_sans_kr_bold)!!
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
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        val latLng = LatLng(location.latitude, location.longitude)
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM_LEVEL))
                    }
                }
        }
    }

    private fun showMissingPermissionError() {
        newInstance(true).show(childFragmentManager, "dialog")
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

        if (isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) || isPermissionGranted(
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

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.allRepairRecord.collect {
                    it.forEach { countRepairDto ->
                        addMarker(countRepairDto)
                    }
                }
            }
        }
    }

    companion object {
        /**
         * Request code for location permission request.
         *
         * @see .onRequestPermissionsResult
         */
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val DEFAULT_ZOOM_LEVEL = 15.0f
    }

    override fun onRepairClick(repairId: Long) {
        //
    }

    override fun onCompleteClick(repairId: Long) {
        val action = MapFragmentDirections.actionMapToMapRepairRecordDetail(repairId)
        findNavController().navigate(action)
    }
}