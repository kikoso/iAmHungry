package com.enrique.iamhungry.di

import com.enrique.iamhungry.core.NetworkHandler
import com.enrique.iamhungry.network.FoursquareService
import com.enrique.iamhungry.repository.VenueRepository
import com.enrique.iamhungry.repository.VenueRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideVenueRepository(
        apiService: FoursquareService,
        networkHandler: NetworkHandler
    ): VenueRepository {
        return VenueRepositoryImpl(apiService, networkHandler)
    }

    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}