package com.enrique.iamhungry.domain.venue

import com.enrique.iamhungry.model.venue.domain.VenueDomain
import com.enrique.iamhungry.repository.VenueRepository
import javax.inject.Inject
import com.enrique.iamhungry.core.Result

class GetVenuesForLocationUseCaseImpl  @Inject constructor(private val venueRepository: VenueRepository) :
    GetVenuesForLocationUseCase {
    override suspend fun getVenuesForLocation(latLng: String): Result<List<VenueDomain>> {
        return venueRepository.getVenueList(latLng)
    }
}