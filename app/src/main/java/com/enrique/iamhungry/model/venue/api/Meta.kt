package com.enrique.iamhungry.model.venue.api

import com.google.gson.annotations.SerializedName

class Meta {

    @SerializedName("code")
    var code: Int = -1

    @SerializedName("requestId")
    var requestId: String = ""
}