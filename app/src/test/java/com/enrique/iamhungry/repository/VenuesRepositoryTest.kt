package com.enrique.iamhungry.repository

import com.enrique.iamhungry.core.NetworkHandler
import com.enrique.iamhungry.model.venue.api.GetVenuesResponse
import com.enrique.iamhungry.network.FoursquareApi
import com.enrique.iamhungry.network.FoursquareService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Response


@RunWith(MockitoJUnitRunner::class)
class VenuesRepositoryTest {

    @MockK
    private lateinit var repository: VenueRepository

    @MockK
    private lateinit var networkHandler: NetworkHandler

    @MockK
    private lateinit var service: FoursquareService

    @MockK
    private lateinit var api: FoursquareApi

    @MockK
    private lateinit var venuesCall: Call<GetVenuesResponse>

    @MockK
    private lateinit var venuesResponse: Response<GetVenuesResponse>

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = VenueRepositoryImpl(service, networkHandler)
    }

    @Test
    fun `the list is empty by default`() {
        every { service.getVenues(any()) } returns venuesCall
        every { api.getVenues(any()) } returns venuesCall
        every { networkHandler.isConnected() } returns true
        every { venuesCall.execute() } returns venuesResponse
        every { venuesResponse.body() } returns null
        every { venuesResponse.isSuccessful } returns true

        runBlocking {
            val venues = repository.getVenueList("42,1")
            verify(exactly = 1) { service.getVenues("42,1") }
        }
    }
}