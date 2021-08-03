package com.enrique.iamhungry

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.enrique.iamhungry.helper.MockitoHelper.anyObject
import com.enrique.iamhungry.core.Failure
import com.enrique.iamhungry.model.venue.domain.VenueDomain
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import com.enrique.iamhungry.core.Result
import com.enrique.iamhungry.domain.venue.GetVenuesForLocationUseCase
import com.enrique.iamhungry.helper.MainCoroutineRule
import com.enrique.iamhungry.model.venue.domain.CategoryDomain
import com.enrique.iamhungry.model.venue.domain.IconDomain
import com.enrique.iamhungry.model.venue.domain.LocationDomain
import com.enrique.iamhungry.ui.map.MapViewModel

import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any


@RunWith(MockitoJUnitRunner::class)
class UserViewModelTest : TestCase() {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var mapViewModel: MapViewModel

    @Mock
    private lateinit var getVenuesForLocationUseCase: GetVenuesForLocationUseCase

    @Before
    fun setup() {
        mapViewModel = MapViewModel(getVenuesForLocationUseCase)
    }

    @Test
    fun `get user success should update data`() {
        val venue =
            VenueDomain(
                "1234",
                "Venue Sample",
                LocationDomain(
                    "Madrid, Spain",
                    "Atocha with Paseo del Prado",
                    40.416775,
                    -3.703790,
                    "28440",
                    "Madrid",
                    "Madrid",
                    "Madrid",
                    "Spain",
                    listOf("Calle Atocha, Madrid, Spain")
                ),
                listOf(
                    CategoryDomain(
                        "1234",
                        "Sushi place",
                        "Sushi places",
                        "Sushi",
                        IconDomain(
                            "https://fastly.4sqi.net/img/general/",
                            "/49659687_Qnj98X5_ToLdh4n-ySnXlgd_uEUKucCX2IcxPtT7UTI.jpg"
                        ),

                        )
                ),
                "https://fastly.4sqi.net/img/general/cap300/49659687_Qnj98X5_ToLdh4n-ySnXlgd_uEUKucCX2IcxPtT7UTI.jpg"
            )
        `when`(runBlocking { getVenuesForLocationUseCase.getVenuesForLocation(anyObject()) }).thenReturn(
            Result.Success(
                listOf(venue)
            )
        )

        mapViewModel.venueList.observeForever {
            assertEquals("1234", it[0].id)
            assertEquals("male", it[0].name)

            assertEquals("Madrid, Spain", it[0].location.address)
            assertEquals("Atocha with Paseo del Prado", it[0].location.crossStreet)
            assertEquals(40.416775, it[0].location.lat)
            assertEquals(-3.703790, it[0].location.lng)
            assertEquals("28440", it[0].location.postalCode)
            assertEquals("Madrid", it[0].location.city)
            assertEquals("Madrid", it[0].location.cc)
            assertEquals("Madrid", it[0].location.state)
            assertEquals("Madrid", it[0].location.country)

            assertEquals("1234", it[0].category[0].id)
            assertEquals("Sushi place", it[0].category[0].name)
            assertEquals("Sushi places", it[0].category[0].pluralName)
            assertEquals("Sushi", it[0].category[0].shortName)

            assertEquals("https://fastly.4sqi.net/img/general/cap300/49659687_Qnj98X5_ToLdh4n-ySnXlgd_uEUKucCX2IcxPtT7UTI.jpg", it[0].pictureUrl)

        }

        mapViewModel.getVenues("42,1")
    }

    @Test
    fun `get user fail error network`() {
        val networkError = Result.Error(Failure.NetworkConnection)
        `when`(runBlocking { getVenuesForLocationUseCase.getVenuesForLocation(any()) }).thenReturn(networkError)
        mapViewModel.networkError.observeForever {
            assertEquals(it, true)
        }
        mapViewModel.featureError.observeForever {
            assertEquals(it, false)
        }
        mapViewModel.generalError.observeForever {
            assertEquals(it, false)
        }
        mapViewModel.getVenues("42,1")
    }


    @Test
    fun `get user fail error general`() {
        val generalError = Result.Error(Failure.GeneralFailure)
        `when`(runBlocking { getVenuesForLocationUseCase.getVenuesForLocation(anyObject()) }).thenReturn(generalError)
        mapViewModel.featureError.observeForever {
            assertEquals(it, false)
        }
        mapViewModel.networkError.observeForever {
            assertEquals(it, false)
        }
        mapViewModel.generalError.observeForever {
            assertEquals(it, true)
        }
        mapViewModel.getVenues("42,1")
    }
}