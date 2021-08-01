package com.enrique.iamhungry.repository

import com.enrique.iamhungry.model.venue.domain.VenueDomain
import com.enrique.iamhungry.core.Result
import com.enrique.iamhungry.model.picture.domain.PictureDomain

interface VenueRepository {
    suspend fun getVenueList(latLng: String) : Result<List<VenueDomain>>
    suspend fun getPictureForVenue(id: String) : Result<List<PictureDomain>>
}