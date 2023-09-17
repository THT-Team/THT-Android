package com.tht.tht.data.service

import com.google.gson.GsonBuilder
import com.tht.tht.data.remote.response.authenticationnumber.AuthenticationNumberResponse
import com.tht.tht.data.remote.response.base.BaseResponse
import com.tht.tht.data.remote.response.base.ErrorResponse
import com.tht.tht.data.remote.retrofit.callAdapter.ApiCallAdapterFactory
import com.tht.tht.data.remote.service.THTSignupApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.File

@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
class AuthenticationNumberServiceTest {

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
    fun `인증번호 요청 Response값을 가져올 수 있다`() = runTest {
        val responseJson =
            File("src/test/java/com/tht/tht/data/resources/authentication/authentication_number.json").readText()
        val response = MockResponse().setBody(responseJson)
        mockWebServer.enqueue(response)

        val expect = BaseResponse.Success(
            statusCode = 200,
            response = AuthenticationNumberResponse(
                phoneNumber = "01012345678",
                authNumber = 628926
            )
        )

        val actual = signupApi.requestAuthenticationNumber("01012345678")
        Assertions.assertThat(actual)
            .isEqualTo(expect)
    }

    @Test
    fun `인증번호 요청을 실패 결과를 가져올 수 있다`() = runTest {
        val responseJson = File(
            "src/test/java/com/tht/tht/data/resources/authentication/authentication_number_failure_400.json"
        ).readText()
        val response = MockResponse()
            .setResponseCode(400)
            .setBody(responseJson)
        mockWebServer.enqueue(response)

        val expect = BaseResponse.ApiError(
            statusCode = 400,
            errorResponse = ErrorResponse(
                status = 400,
                error = "인증번호를 보낼 수 없습니다.",
                message = "인증번호를 보낼 수 없습니다.",
                timestamp = "123456789",
                path = "..."
            )
        )

        val actual = signupApi.fetchIdealType()
        Assertions.assertThat(actual)
            .isEqualTo(expect)
    }
}
