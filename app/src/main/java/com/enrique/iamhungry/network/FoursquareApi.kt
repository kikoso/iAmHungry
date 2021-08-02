package com.enrique.iamhungry.network

import com.enrique.iamhungry.BuildConfig
import com.enrique.iamhungry.model.picture.api.GetPicturesResponse
import com.enrique.iamhungry.model.venue.api.GetVenuesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FoursquareApi {

    companion object {
        private const val VENUES_LIMIT = 5
        private const val RADIUS_LIMIT = 500
        private const val VERSION = "20210409"
        private const val FOOD_CATEGORY = "categoryId=4d4b7105d754a06374d81259"

        private const val PICTURE_LIMIT = 1

        private const val REQUEST_COMMON_PARAMS =
            "client_id=${BuildConfig.FOURSQUARE_CLIENT_ID}&client_secret=${BuildConfig.FOURSQUARE_CLIENT_SECRET}&v=$VERSION"
    }

    @GET("/v2/venues/search?$REQUEST_COMMON_PARAMS&limit=$VENUES_LIMIT&$FOOD_CATEGORY&radius=$RADIUS_LIMIT")
    fun getVenues(@Query("ll") latLng: String): Call<GetVenuesResponse>

    @GET("/v2/venues/{id}/photos?$REQUEST_COMMON_PARAMS&limit=$PICTURE_LIMIT")
    fun getPictureForVenue(@Path("id") id: String): Call<GetPicturesResponse>
}