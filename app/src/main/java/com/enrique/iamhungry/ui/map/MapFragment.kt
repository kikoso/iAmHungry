package com.enrique.iamhungry.ui.map

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enrique.iamhungry.R
import com.enrique.iamhungry.databinding.FragmentMapBinding
import com.enrique.iamhungry.model.venue.view.VenueView
import com.enrique.iamhungry.utils.viewBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {
    private val binding by viewBinding(FragmentMapBinding::bind)
    private val viewModel: MapViewModel by viewModels()
    private var adapter = VenuesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        binding.venuesRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.venuesRecyclerView.adapter = adapter

        setUpVenuesListener()
    }

    private fun setUpVenuesListener() {
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
       map.setOnCameraIdleListener {
           val latLng: LatLng = map.cameraPosition.target
            viewModel.getVenues(latLng.latitude.toString() +","+ latLng.longitude.toString() )
       }
    }
}