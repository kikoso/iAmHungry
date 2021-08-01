package com.enrique.iamhungry.model.venue.view

import com.google.android.gms.maps.model.LatLng

data class VenueView(
   var id : String,
   var name : String,
   var location : LocationView,
   var category: List<CategoryView>,
   var pictureUrl: String
) {
   fun getLatLng() = LatLng(location.lat, location.lng)
}