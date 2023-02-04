package com.tht.tht.data.service

import com.google.gson.GsonBuilder
import com.tht.tht.data.remote.response.base.BaseResponse
import com.tht.tht.data.remote.response.base.ErrorResponse
import com.tht.tht.data.remote.response.base.SuccessResponse
import com.tht.tht.data.remote.response.ideal.IdealTypeResponse
import com.tht.tht.data.remote.retrofit.callAdapter.ApiCallAdapterFactory
import com.tht.tht.data.remote.service.ThtApi
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

@ExperimentalCoroutinesApi
class IdealTypeServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var thtApi: ThtApi

    @Before
    fun setupTest() {
        mockWebServer = MockWebServer()
        thtApi = Retrofit.Builder()
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
    fun `이상형 정보를 가져올 수 있다`() = runTest {
        val responseJson = File("src/test/java/com/tht/tht/data/resources/ideal.json").readText()
        val response = MockResponse().setBody(responseJson)
        mockWebServer.enqueue(response)

        val expect = BaseResponse.Success(
            statusCode = 200,
            response = SuccessResponse(
                status = 200,
                body = listOf(
                    IdealTypeResponse(
                        name = "지적인",
                        emojiCode = "1F9E0",
                        idx = 1
                    ),
                    IdealTypeResponse(
                        name = "귀여운",
                        emojiCode = "1F63B",
                        idx = 2
                    ),
                    IdealTypeResponse(
                        name = "피부가 좋은",
                        emojiCode = "2728",
                        idx = 3
                    )
                )
            )
        )

        val actual = thtApi.fetchIdealType()
        assertThat(actual)
            .isEqualTo(expect)
    }

    @Test
    fun `이상형 정보를 가져올 수 없다`() = runTest {
        val responseJson = File("src/test/java/com/tht/tht/data/resources/ideal_failure_404.json").readText()
        val response = MockResponse()
            .setResponseCode(404)
            .setBody(responseJson)
        mockWebServer.enqueue(response)

        val expect = BaseResponse.ApiError(
            statusCode = 404,
            errorResponse = ErrorResponse(
                status = 404,
                error = "이상형 정보를 찾을 수 없습니다.",
                message = "이상형 정보를 찾을 수 없습니다.",
                timestamp = "123456789",
                path = "..."
            )
        )

        val actual = thtApi.fetchIdealType()
        assertThat(actual)
            .isEqualTo(expect)
    }
}
