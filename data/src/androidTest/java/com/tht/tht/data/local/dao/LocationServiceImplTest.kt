package com.tht.tht.data.local.dao

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.tht.tht.data.remote.response.location.LocationResponse
import com.tht.tht.data.remote.service.LocationService
import com.tht.tht.data.remote.service.LocationServiceImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class LocationServiceImplTest {

    private lateinit var context: Context
    private lateinit var locationService: LocationService
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setup() {
        context = getApplicationContext<Application>()
        locationService = LocationServiceImpl(context)
    }

    @Test
    fun fetchLocationByAddress_return_location() = runTest(testDispatcher) {
        val expect = LocationResponse(37.5131008, 127.10343340000001, "서울 송파구 올림픽로 300")
        val actual = locationService.fetchLocationByAddress("서울 송파구 올림픽로 300")
        assertThat(actual)
            .isEqualTo(expect)
    }
}
