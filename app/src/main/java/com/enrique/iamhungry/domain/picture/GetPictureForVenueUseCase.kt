package com.enrique.iamhungry.domain.picture

import com.enrique.iamhungry.core.Result
import com.enrique.iamhungry.model.picture.domain.PictureDomain

interface GetPictureForVenueUseCase {
    suspend fun getPictureForVenue(id: String) : Result<List<PictureDomain>>
}