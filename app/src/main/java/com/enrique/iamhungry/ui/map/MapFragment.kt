package com.enrique.iamhungry.ui.map

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enrique.iamhungry.R
import com.enrique.iamhungry.databinding.FragmentMapBinding
import com.enrique.iamhungry.model.app.AppState
import com.enrique.iamhungry.model.venue.view.VenueView
import com.enrique.iamhungry.utils.Constants.DEFAULT_LATITUDE
import com.enrique.iamhungry.utils.Constants.DEFAULT_LONGITUDE
import com.enrique.iamhungry.utils.Constants.DEFAULT_ZOOM
import com.enrique.iamhungry.utils.viewBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {
    private val binding by viewBinding(FragmentMapBinding::bind)
    private val viewModel: MapViewModel by viewModels()
    private var adapter = VenuesAdapter(::onVenueClicked)

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getLastKnownLocation()
            } else {
                moveMap()
            }
        }

    private fun onVenueClicked(venue: VenueView) {
        map?.clear()
        viewModel.onVenueSelected(venue)
    }

    private var map: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        binding.venuesRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.venuesRecyclerView.adapter = adapter
        binding.venueDetails.setOnCloseItemClickedListener { viewModel.onBackPressed() }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        setUpVenuesListener()
        checkLocationPermission()

        activity?.onBackPressedDispatcher?.addCallback { viewModel.onBackPressed() }
    }

    private fun setUpVenuesListener() {
        viewModel.currentState.observe(
            viewLifecycleOwner, {
                renderAppState(it)
            }
        )
        viewModel.loading.observe(
            viewLifecycleOwner, {
                renderLoadingState(it)
            }
        )
        viewModel.venueList.observe(
            viewLifecycleOwner, {
                renderVenueList(it)
            }
        )
        viewModel.venueDetails.observe(
            viewLifecycleOwner, {
                renderVenueDetails(it)
            }
        )
        viewModel.networkError.observe(
            viewLifecycleOwner, {
                renderNetworkError(it)
            }
        )
        viewModel.featureError.observe(
            viewLifecycleOwner, {
                renderFeatureError(it)
            }
        )
        viewModel.generalError.observe(
            viewLifecycleOwner, {
                renderGeneralError(it)
            }
        )
    }

    private fun renderAppState(it: AppState?) {
        if (it == AppState.Exploration) {
            binding.venueDetails.animate()
                .translationY(0f)
                .alpha(0.0f)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        binding.venueDetails.visibility = View.VISIBLE
                    }
                })
            binding.centerLocationMarker.visibility = View.GONE
        } else if (it == AppState.Navigation) {
            map?.clear()
            binding.venueDetails.animate()
                .translationY(0f)
                .alpha(1.0f)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        binding.venueDetails.visibility = View.GONE
                    }
                })
            binding.centerLocationMarker.visibility = View.VISIBLE
            moveMapToCurrentLocation()
        } else if (it == AppState.Finish) {
            requireActivity().finish()
        }
    }

    private fun renderVenueDetails(venue: VenueView) {
        binding.venueDetails.setData(venue)
        addCurrentVenueMarker(venue)
    }

    private fun addCurrentVenueMarker(venue: VenueView) {
        map?.addMarker(
            MarkerOptions().position(venue.getLatLng())
        )
        map?.animateCamera(CameraUpdateFactory.newLatLng(venue.getLatLng()))
    }

    private fun renderLoadingState(loading: Boolean) {
        if (loading) {
            binding.loading.visibility = View.VISIBLE
        } else {
            binding.loading.visibility = View.GONE
        }
    }

    private fun renderError(error: Boolean, msg: String, errorTv: TextView) {
        if (error) {
            binding.venuesRecyclerView.visibility = View.GONE
            errorTv.visibility = View.VISIBLE
            errorTv.text = msg
        } else {
            errorTv.visibility = View.GONE
        }
    }

    private fun renderNetworkError(error: Boolean) {
        renderError(error, getString(R.string.network_error), binding.tvError)
    }

    private fun renderFeatureError(error: Boolean) {
        renderError(error, getString(R.string.general_error), binding.tvError)
    }

    private fun renderGeneralError(error: Boolean) {
        renderError(error, getString(R.string.general_error), binding.tvError)
    }

    private fun renderVenueList(venueList: MutableList<VenueView>) {
        binding.tvError.visibility = View.GONE
        binding.loading.visibility = View.GONE
        binding.venuesRecyclerView.visibility = View.VISIBLE
        adapter.venues.clear()
        adapter.venues.addAll(venueList)
        adapter.notifyDataSetChanged()
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        map.setOnCameraIdleListener {
            moveMapToCurrentLocation()
        }
    }

    private fun moveMapToCurrentLocation() {
        val latLng: LatLng? = map?.cameraPosition?.target
        latLng?.latitude?.let { viewModel.moveMap(latLng.latitude, latLng.longitude) }
    }

    private fun moveMap(
        latitude: Double = DEFAULT_LATITUDE,
        longitude: Double = DEFAULT_LONGITUDE
    ) {
        binding.loading.visibility = View.GONE
        map?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(latitude, longitude),
                DEFAULT_ZOOM
            )
        )
    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                getLastKnownLocation()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                //TODO: explain why we need this permission

            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }
    }

    private fun getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkLocationPermission()
            return
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        map?.isMyLocationEnabled = true
                        map?.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    location.latitude,
                                    location.longitude
                                ), DEFAULT_ZOOM
                            )
                        )
                    }
                }
        }
    }
}