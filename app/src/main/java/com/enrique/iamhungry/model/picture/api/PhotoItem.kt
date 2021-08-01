package com.enrique.iamhungry.model.picture.api

import com.enrique.iamhungry.model.picture.domain.PictureDomain
import com.google.gson.annotations.SerializedName

class PhotoItem {
    @SerializedName("prefix")
    var prefix: String = ""

    @SerializedName("suffix")
    var suffix: String = ""

    fun toPictureDomain() = PictureDomain(
        suffix = suffix,
        prefix = prefix
    )
}