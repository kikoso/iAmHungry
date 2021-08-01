package com.enrique.iamhungry.model.venue.view

data class LocationView(
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
)
