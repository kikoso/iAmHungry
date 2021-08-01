package com.enrique.iamhungry.model.picture.api

import com.google.gson.annotations.SerializedName

class PicturesResponse {
    @SerializedName("photos")
    var photos : PhotoWrapper = PhotoWrapper()
}