package com.enrique.iamhungry.repository

import com.enrique.iamhungry.core.Failure
import com.enrique.iamhungry.model.venue.domain.VenueDomain
import com.enrique.iamhungry.network.FoursquareService
import com.enrique.iamhungry.core.Result
import com.enrique.iamhungry.model.picture.domain.PictureDomain
import com.enrique.iamhungry.model.venue.api.Venue

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import javax.inject.Inject

class VenueRepositoryImpl @Inject constructor(
    private val apiService: FoursquareService
) :
    VenueRepository {

    override suspend fun getVenueList(latLng: String): Result<List<VenueDomain>> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getVenues(latLng).execute()
            when {
                response.isSuccessful -> {
                    response.body()?.let { getVenuesResponse ->
                        val listVenuesDomain = mutableListOf<VenueDomain>()
                        getVenuesResponse.response.venues.forEach {
                            val venue = it.toVenueDomain()

                            when (val photoResult = getVenuePhoto(it)) {
                                is Result.Success<String> -> venue.pictureUrl = photoResult.response
                                is Result.Error -> print("Error")//
                            }
                            listVenuesDomain.add(venue)
                        }

                        return@withContext Result.Success(listVenuesDomain)
                    }
                    return@withContext Result.Success(emptyList())
                }
                else -> return@withContext Result.Error(Failure.NetworkConnection)
            }
        }
    }

    private suspend fun getVenuePhoto(it: Venue): Result<String> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getPictureForVenue(it.id).execute()
            when {
                response.isSuccessful -> {
                    response.body()?.let { getPictureForVenueResponse ->
                        return@withContext Result.Success(getPictureForVenueResponse.getMainPictureForVenue())
                    }
                    return@withContext Result.Success("")
                }
                else -> return@withContext Result.Error(Failure.NetworkConnection)
            }
        }
    }

    override suspend fun getPictureForVenue(id: String): Result<List<PictureDomain>> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getPictureForVenue(id).execute()
            when {
                response.isSuccessful -> {
                    response.body()?.let { getPictureForVenueResponse ->
                        val listPhotoItemDomain = mutableListOf<PictureDomain>()
                        getPictureForVenueResponse.response.photos.items.forEach {
                            listPhotoItemDomain.add(it.toPictureDomain())
                        }

                        return@withContext Result.Success(listPhotoItemDomain)
                    }
                    return@withContext Result.Success(emptyList())
                }
                else -> return@withContext Result.Error(Failure.NetworkConnection)
            }
        }
    }
}