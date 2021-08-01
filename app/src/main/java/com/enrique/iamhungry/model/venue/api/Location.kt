package com.enrique.iamhungry.model.venue.api

import com.enrique.iamhungry.model.venue.domain.LocationDomain
import com.google.gson.annotations.SerializedName

class Location{
    @SerializedName("address")
    var address : String = ""

    @SerializedName("crossStreet")
    var crossStreet : String = ""

    @SerializedName("lat")
    val lat: Double  = 0.0

    @SerializedName("lng")
    val lng: Double  = 0.0

    @SerializedName("postalCode")
    val postalCode: String = ""

    @SerializedName("cc")
    val cc: String = ""

    @SerializedName("city")
    val city: String? = ""

    @SerializedName("state")
    val state: String? = ""

    @SerializedName("country")
    val country: String? = ""

    @SerializedName("formattedAddress")
    val formattedAddress: List<String> = emptyList()

    fun toLocationDomain() = LocationDomain(
        address = address,
        crossStreet = crossStreet,
        lat = lat,
        lng = lng,
        postalCode = postalCode,
        cc = cc,
        city = city,
        state = state,
        country = country,
        formattedAddress = formattedAddress.map {
            it
        }
    )
}
