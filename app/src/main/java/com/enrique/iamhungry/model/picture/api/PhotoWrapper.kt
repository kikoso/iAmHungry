package com.enrique.iamhungry.model.picture.api

import com.google.gson.annotations.SerializedName

class PhotoWrapper {
    @SerializedName("count")
    var count: Int = -1

    @SerializedName("items")
    var items: List<PhotoItem> = emptyList()
}
