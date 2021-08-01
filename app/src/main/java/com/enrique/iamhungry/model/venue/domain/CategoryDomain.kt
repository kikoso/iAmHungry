package com.enrique.iamhungry.model.venue.domain

import com.enrique.iamhungry.model.venue.view.CategoryView

class CategoryDomain(
    val id: String,
    val name: String,
    val pluralName: String,
    val shortName: String,
    val icon: IconDomain,
    val primary: Boolean = false
) {
    fun toCategoryView() = CategoryView(
        id = id,
        name = name,
        pluralName = pluralName,
        shortName = shortName,
        icon = icon.toIconView(),
        primary = primary
    )
}