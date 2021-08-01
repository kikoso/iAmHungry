package com.enrique.iamhungry.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enrique.iamhungry.core.Failure
import com.enrique.iamhungry.domain.venue.GetVenuesForLocationUseCase
import com.enrique.iamhungry.core.Result
import com.enrique.iamhungry.model.venue.domain.VenueDomain
import com.enrique.iamhungry.model.venue.view.VenueView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val getVenuesForLocationUseCase: GetVenuesForLocationUseCase) : ViewModel() {

    private val _venueList = MutableLiveData<MutableList<VenueView>>()
    val venueList: LiveData<MutableList<VenueView>> = _venueList

    private val _venueDetails = MutableLiveData<VenueView>()
    val venueDetails: LiveData<VenueView> = _venueDetails

    private val _loading = MutableLiveData<Boolean>()
    val loading = _loading

    private val _networkError = MutableLiveData<Boolean>()
    val networkError = _networkError

    private val _generalError = MutableLiveData<Boolean>()
    val generalError = _generalError

    private val _featureError = MutableLiveData<Boolean>()
    val featureError = _featureError

    fun getVenues(latLng: String) {
        _loading.postValue(true)
        viewModelScope.launch {
            when (val result = getVenuesForLocationUseCase.getVenuesForLocation(latLng)) {
                is Result.Success<List<VenueDomain>> -> onGetVenueSuccess(result.response)
                is Result.Error -> onGetVenueFail(result.failure)
            }
        }
    }

    private fun onGetVenueSuccess(venueDomain: List<VenueDomain>) {
        val venuesViewList = mutableListOf<VenueView>()
        venueDomain.forEach {
            venuesViewList.add(it.toVenueView())
        }
        _venueList.postValue(venuesViewList)
    }

    private fun onGetVenueFail(failure: Failure) {
        _loading.postValue(false)
        when (failure) {
            is Failure.FeatureFailure -> featureError.postValue(true)
            is Failure.GeneralFailure -> generalError.postValue(true)
            is Failure.NetworkConnection -> networkError.postValue(true)
        }
    }

    fun onVenueSelected(venue: VenueView) {
        _venueDetails.postValue(venue)
    }
}