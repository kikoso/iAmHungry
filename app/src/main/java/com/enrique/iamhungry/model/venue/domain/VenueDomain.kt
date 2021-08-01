package com.enrique.iamhungry.model.venue.domain

import com.enrique.iamhungry.model.venue.view.VenueView

class VenueDomain(
   var id : String,
   var name : String,
   var location : LocationDomain,
   var category: List<CategoryDomain>,
   var pictureUrl: String = ""
) {
   fun toVenueView() = VenueView(
      id = id,
      name = name,
      location = location.toLocationView(),
      category = category.map { it.toCategoryView() },
      pictureUrl = pictureUrl
   )
}