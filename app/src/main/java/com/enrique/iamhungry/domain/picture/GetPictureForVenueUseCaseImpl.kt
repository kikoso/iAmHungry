package com.enrique.iamhungry.domain.picture

import com.enrique.iamhungry.repository.VenueRepository
import javax.inject.Inject
import com.enrique.iamhungry.core.Result
import com.enrique.iamhungry.model.picture.domain.PictureDomain

class GetPictureForVenueUseCaseImpl @Inject constructor(private val venueRepository: VenueRepository) :
    GetPictureForVenueUseCase {
    override suspend fun getPictureForVenue(id: String): Result<List<PictureDomain>> {
        return venueRepository.getPictureForVenue(id)
    }
}

