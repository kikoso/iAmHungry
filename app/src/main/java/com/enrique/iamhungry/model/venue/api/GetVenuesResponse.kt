package com.enrique.iamhungry.model.venue.api

import com.google.gson.annotations.SerializedName

class GetVenuesResponse {
    @SerializedName("meta")
    var meta: Meta = Meta()

    @SerializedName("response")
    var response: VenuesResponse = VenuesResponse()
}