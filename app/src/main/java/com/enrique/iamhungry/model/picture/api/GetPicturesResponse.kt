package com.enrique.iamhungry.model.picture.api

import com.enrique.iamhungry.model.venue.api.Meta
import com.google.gson.annotations.SerializedName

class GetPicturesResponse {
    @SerializedName("meta")
    var meta: Meta = Meta()

    @SerializedName("response")
    var response: PicturesResponse = PicturesResponse()

    fun getMainPictureForVenue() : String {
        return response.photos.items[0].prefix + response.photos.items[0].suffix
    }
}