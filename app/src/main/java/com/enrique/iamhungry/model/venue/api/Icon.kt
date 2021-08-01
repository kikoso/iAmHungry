package com.enrique.iamhungry.model.venue.api

import com.enrique.iamhungry.model.venue.domain.IconDomain
import com.google.gson.annotations.SerializedName

class Icon {
    @SerializedName("prefix")
    val prefix: String = ""

    @SerializedName("suffix")
    val suffix: String = ""

    fun toIconDomain() = IconDomain(
        prefix = prefix,
        suffix = suffix
    )
}