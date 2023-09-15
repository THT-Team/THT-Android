package com.tht.tht.data.datasource

import com.tht.tht.data.remote.datasource.signup.LocationDataSourceImpl
import com.tht.tht.data.remote.response.location.LocationResponse
import com.tht.tht.data.remote.service.location.LocationService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
internal class LocationDataSourceImplTest {

    private lateinit var dataSource: LocationDataSourceImpl
    private lateinit var locationService: LocationService
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setUpTest() {
        locationService = mockk(relaxed = true)
        dataSource = LocationDataSourceImpl(
            testDispatcher,
            locationService
        )
    }

    @Test
    fun `fetchCurrentLocation은 LocationService의 fetchCurrentLocation의 결과를 리턴한다`() = runTest(testDispatcher) {
        val expect = LocationResponse(
            0.0,
            0.0,
            ""
        )
        coEvery { locationService.fetchCurrentLocation() } returns expect
        val actual = dataSource.fetchCurrentLocation()

        assertThat(actual)
            .isEqualTo(expect)
    }

    @Test
    fun `fetchLocationByAddress은 LocationService의 fetchLocationByAddress의 결과를 리턴한다`() = runTest(testDispatcher) {
        val expect = LocationResponse(
            0.0,
            0.0,
            ""
        )
        coEvery { locationService.fetchLocationByAddress(expect.address) } returns expect
        val actual = dataSource.fetchLocationByAddress(expect.address)

        assertThat(actual)
            .isEqualTo(expect)
    }
}
