package com.enrique.iamhungry.model.picture.api

import com.enrique.iamhungry.model.venue.api.Meta
import com.google.gson.annotations.SerializedName

class GetPicturesResponse {
    @SerializedName("meta")
    var meta: Meta = Meta()

    @SerializedName("response")
    var response: PicturesResponse = PicturesResponse()

    fun getMainPictureForVenue() =
        if (response.photos.items.isNotEmpty()) {
            response.photos.items[0].prefix + "cap100"+ response.photos.items[0].suffix
        } else {
            ""
        }
}