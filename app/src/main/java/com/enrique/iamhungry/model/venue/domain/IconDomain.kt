package com.enrique.iamhungry.model.venue.domain

import com.enrique.iamhungry.model.venue.view.IconView

class IconDomain(
    val prefix: String,
    val suffix: String,
) {
    fun toIconView() = IconView(
        prefix = prefix,
        suffix = suffix
    )
}