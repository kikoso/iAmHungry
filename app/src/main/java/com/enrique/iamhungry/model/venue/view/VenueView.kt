package com.enrique.iamhungry.model.venue.view

data class VenueView(
   var id : String,
   var name : String,
   var location : LocationView,
   var category: List<CategoryView>,
   var pictureUrl: String
)