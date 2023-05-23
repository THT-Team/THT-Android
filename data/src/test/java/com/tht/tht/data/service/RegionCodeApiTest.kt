package com.tht.tht.data.service

import com.google.gson.GsonBuilder
import com.tht.tht.data.remote.response.base.BaseResponse
import com.tht.tht.data.remote.response.base.ErrorResponse
import com.tht.tht.data.remote.response.regioncode.RegionCodeResponse
import com.tht.tht.data.remote.retrofit.callAdapter.ApiCallAdapterFactory
import com.tht.tht.data.remote.service.RegionCodeApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.File

@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
class RegionCodeApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var regionCodeApi: RegionCodeApi

    @Before
    fun setUpTest() {
        mockWebServer = MockWebServer()
        regionCodeApi = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .create()
                )
            )
            .addCallAdapterFactory(ApiCallAdapterFactory())
            .baseUrl(mockWebServer.url(""))
            .build()
            .create()
    }

    @Test
    fun `지역 코드를 가져올 수 있다`() = runTest {
        val responseJson = File("src/test/java/com/tht/tht/data/resources/regioncode/region_code.json").readText()
        val response = MockResponse().setBody(responseJson)
        mockWebServer.enqueue(response)

        val expect = BaseResponse.Success(
            statusCode = 200,
            response = RegionCodeResponse(
                listOf(
                    RegionCodeResponse.StanReginCd(
                        null
                    ),
                    RegionCodeResponse.StanReginCd(
                        listOf(
                            RegionCodeResponse.StanReginCd.Row("4113300000")
                        )
                    )
                )
            )
        )

        val actual = regionCodeApi.fetchRegionCode("경기도 성남시 중원구")
        assertThat(actual)
            .isEqualTo(expect)
    }

    @Test
    fun `지역 코드를 가져올 수 없다`() = runTest {
        val responseJson = File("src/test/java/com/tht/tht/data/resources/regioncode/region_code_failure_400.json").readText()
        val response = MockResponse()
            .setResponseCode(400)
            .setBody(responseJson)
        mockWebServer.enqueue(response)

        val expect = BaseResponse.ApiError(
            statusCode = 400,
            errorResponse = ErrorResponse(
                status = 400,
                error = "주소 형식이 잘못되었습니다.",
                message = "주소 형식이 잘못되었습니다.",
                timestamp = "123456789",
                path = "..."
            )
        )

        val actual = regionCodeApi.fetchRegionCode("경기도 성남시 중원구")
        assertThat(actual)
            .isEqualTo(expect)
    }
}
