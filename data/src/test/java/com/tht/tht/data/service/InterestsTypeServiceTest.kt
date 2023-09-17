package com.tht.tht.data.service

import com.google.gson.GsonBuilder
import com.tht.tht.data.remote.response.base.BaseResponse
import com.tht.tht.data.remote.response.base.ErrorResponse
import com.tht.tht.data.remote.response.interests.InterestTypeResponse
import com.tht.tht.data.remote.retrofit.callAdapter.ApiCallAdapterFactory
import com.tht.tht.data.remote.service.THTSignupApi
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
class InterestsTypeServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var signupApi: THTSignupApi

    @Before
    fun setupTest() {
        mockWebServer = MockWebServer()
        signupApi = Retrofit.Builder()
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
    fun `관심사 정보를 가져올 수 있다`() = runTest {
        val responseJson =
            File("src/test/java/com/tht/tht/data/resources/interests/interests.json").readText()
        val response = MockResponse().setBody(responseJson)
        mockWebServer.enqueue(response)

        val expect = BaseResponse.Success(
            statusCode = 200,
            response = listOf(
                InterestTypeResponse(
                    name = "게임",
                    emojiCode = "1F3AE",
                    idx = 1
                ),
                InterestTypeResponse(
                    name = "독서",
                    emojiCode = "1F4DA",
                    idx = 2
                ),
                InterestTypeResponse(
                    name = "영화/드라마",
                    emojiCode = "1F3AC",
                    idx = 3
                )
            )
        )

        val actual = signupApi.fetchInterestsType()
        assertThat(actual)
            .isEqualTo(expect)
    }

    @Test
    fun `관심사 정보를 가져올 수 없다`() = runTest {
        val responseJson =
            File("src/test/java/com/tht/tht/data/resources/interests/interests_failure_404.json").readText()
        val response = MockResponse()
            .setResponseCode(404)
            .setBody(responseJson)
        mockWebServer.enqueue(response)

        val expect = BaseResponse.ApiError(
            statusCode = 404,
            errorResponse = ErrorResponse(
                status = 404,
                error = "관심사 정보를 찾을 수 없습니다.",
                message = "관심사 정보를 찾을 수 없습니다.",
                timestamp = "123456789",
                path = "..."
            )
        )

        val actual = signupApi.fetchInterestsType()
        assertThat(actual)
            .isEqualTo(expect)
    }
}
