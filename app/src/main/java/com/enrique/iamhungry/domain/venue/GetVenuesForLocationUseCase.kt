package com.enrique.iamhungry.domain.venue

import com.enrique.iamhungry.model.venue.domain.VenueDomain
import com.enrique.iamhungry.core.Result

interface GetVenuesForLocationUseCase {
    suspend fun getVenuesForLocation(latLng: String) : Result<List<VenueDomain>>
}