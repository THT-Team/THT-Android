package com.tht.tht.data.repository

import com.tht.tht.data.local.mapper.toModel
import com.tht.tht.data.remote.datasource.signup.LocationDataSource
import com.tht.tht.data.remote.response.location.LocationResponse
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
internal class LocationRepositoryImplTest {

    private lateinit var dataSource: LocationDataSource
    private lateinit var repository: LocationRepositoryImpl
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setUp() {
        dataSource = mockk(relaxed = true)
        repository = LocationRepositoryImpl(
            testDispatcher,
            dataSource
        )
    }

    @Test
    fun `fetchCurrentLocation은 LocationDataSource의 fetchCurrentLocation의 결과를 LocationModel로 변환해 리턴한다`() = runTest(testDispatcher) {
        val expect = mockk<LocationResponse>(relaxed = true)
        coEvery { dataSource.fetchCurrentLocation() } returns expect
        val actual = repository.fetchCurrentLocation()
        assertThat(actual).
            isEqualTo(expect.toModel())
    }

    @Test
    fun `fetchLocationByAddress LocationDataSource의 fetchLocationByAddress의 결과를 LocationModel로 변환해 리턴한다`() = runTest(testDispatcher) {
        val expect = mockk<LocationResponse>(relaxed = true)
        coEvery { dataSource.fetchLocationByAddress(expect.address) } returns expect
        val actual = repository.fetchLocationByAddress(expect.address)
        assertThat(actual).
            isEqualTo(expect.toModel())
    }
}
