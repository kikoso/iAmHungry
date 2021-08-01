package com.enrique.iamhungry.network

import com.enrique.iamhungry.model.picture.api.GetPicturesResponse
import com.enrique.iamhungry.model.venue.api.GetVenuesResponse
import retrofit2.Call
import javax.inject.Inject

class FoursquareService @Inject constructor(private val api: FoursquareApi){
    fun getVenues(latLng: String): Call<GetVenuesResponse> {
        return api.getVenues(latLng)
    }

    fun getPictureForVenue(id: String): Call<GetPicturesResponse> {
        return api.getPictureForVenue(id)
    }
}