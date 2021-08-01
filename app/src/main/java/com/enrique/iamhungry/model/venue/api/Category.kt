package com.enrique.iamhungry.model.venue.api

import com.enrique.iamhungry.model.venue.domain.CategoryDomain
import com.google.gson.annotations.SerializedName

class Category {
    @SerializedName("id")
    val id: String = ""

    @SerializedName("name")
    val name: String = ""

    @SerializedName("pluralName")
    val pluralName: String = ""

    @SerializedName("shortName")
    val shortName: String = ""

    @SerializedName("icon")
    val icon: Icon = Icon()

    @SerializedName("primary")
    val primary: Boolean = false

    fun toCategoryDomain() = CategoryDomain(
        id = id,
        name = name,
        pluralName = pluralName,
        shortName = shortName,
        icon = icon.toIconDomain(),
        primary = primary
    )
}