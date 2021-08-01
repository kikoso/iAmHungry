package com.enrique.iamhungry.model.venue.domain

import com.enrique.iamhungry.model.venue.view.LocationView

class LocationDomain(
    var address: String,
    var crossStreet: String,
    val lat: Double,
    val lng: Double,
    val postalCode: String,
    val cc: String,
    val city: String?,
    val state: String?,
    val country: String?,
    val formattedAddress: List<String>
) {
    fun toLocationView() = LocationView(
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


