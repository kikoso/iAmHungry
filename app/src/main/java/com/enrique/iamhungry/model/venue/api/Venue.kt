package com.enrique.iamhungry.model.venue.api

import com.enrique.iamhungry.model.venue.domain.VenueDomain
import com.google.gson.annotations.SerializedName

class Venue {
    @SerializedName("id")
    var id: String = ""

    @SerializedName("name")
    var name: String = ""

    @SerializedName("location")
    var location: Location = Location()

    @SerializedName("categories")
    var categories: List<Category> = emptyList()

    fun toVenueDomain() = VenueDomain(
        id = id,
        name = name,
        location = location.toLocationDomain(),
        category = categories.map { it.toCategoryDomain() }
    )
}