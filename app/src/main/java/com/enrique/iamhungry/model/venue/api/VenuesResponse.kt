package com.enrique.iamhungry.model.venue.api

import com.google.gson.annotations.SerializedName

class VenuesResponse {
    @SerializedName("venues")
    var venues : List<Venue> = emptyList()
}