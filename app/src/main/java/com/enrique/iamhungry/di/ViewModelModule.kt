package com.enrique.iamhungry.di

import com.enrique.iamhungry.domain.picture.GetPictureForVenueUseCase
import com.enrique.iamhungry.domain.picture.GetPictureForVenueUseCaseImpl
import com.enrique.iamhungry.domain.venue.GetVenuesForLocationUseCase
import com.enrique.iamhungry.domain.venue.GetVenuesForLocationUseCaseImpl
import com.enrique.iamhungry.repository.VenueRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Singleton
    @Provides
    fun provideVenueUseCase(
        venueRepository: VenueRepository
    ): GetVenuesForLocationUseCase {
        return GetVenuesForLocationUseCaseImpl(venueRepository)
    }

    @Singleton
    @Provides
    fun providePictureUseCase(
        venueRepository: VenueRepository
    ): GetPictureForVenueUseCase {
        return GetPictureForVenueUseCaseImpl(venueRepository)
    }

}