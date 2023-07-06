package com.tht.tht.domain.login.usecase

import com.tht.tht.domain.login.repository.LoginRepository
import com.tht.tht.domain.token.model.FcmTokenLoginResponseModel
import com.tht.tht.domain.token.repository.TokenRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
internal class RefreshFcmTokenLoginUseCaseTest {

    private lateinit var useCase: RefreshFcmTokenLoginUseCase
    private lateinit var tokenRepository: TokenRepository
    private lateinit var loginRepository: LoginRepository

    @Before
    fun setUp() {
        tokenRepository = mockk(relaxed = true)
        loginRepository = mockk(relaxed = true)
        useCase = RefreshFcmTokenLoginUseCase(
            tokenRepository, loginRepository
        )
    }

    @Test
    fun `useCase는 tokenRepository의 updateFcmToken를 호출한다`() = runTest {
        useCase("fcmToken")
        coVerify(exactly = 1) { tokenRepository.updateFcmToken(any()) }
    }

    @Test
    fun `useCase는 매개변수 fcmToken를 tokenRepository의 updateFcmToken에 전달한다`() = runTest {
        val token = "fcmToken"
        useCase(token)
        coVerify(exactly = 1) { tokenRepository.updateFcmToken(token) }
    }

    @Test
    fun `useCase는 tokenRepository의 fetchPhone를 호출한다`() = runTest {
        useCase("fcmToken")
        coVerify(exactly = 1) { tokenRepository.fetchPhone() }
    }

    @Test
    fun `useCase는 tokenRepository의 fetchPhone가 null을 리턴하면 발생하는 예외를 Result로 래핑해 리턴한다`() = runTest {
        coEvery { tokenRepository.fetchPhone() } returns null

        val actual = useCase("fcmToken")

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull())
            .isNotNull
            .isInstanceOf(Exception::class.java)
    }

    @Test
    fun `useCase는 tokenRepository의 fetchPhone가 null이 아니면 loginRepository의 requestFcmTokenLogin를 호출한다`() = runTest {
        coEvery { tokenRepository.fetchPhone() } returns "phone"
        useCase("fcmToken")
        coVerify(exactly = 1) { loginRepository.refreshFcmTokenLogin(any(), any()) }
    }

    @Test
    fun `useCase는 매개변수 token과 fetchPhone의 리턴값 phone을 loginRepository의 requestFcmTokenLogin에 전달한다`() = runTest {
        val phone = "phone"
        val token = "fcmToken"
        coEvery { tokenRepository.fetchPhone() } returns phone
        useCase(token)
        coVerify(exactly = 1) { loginRepository.refreshFcmTokenLogin(token, phone) }
    }

    @Test
    fun `useCase는 loginRepository의 requestFcmTokenLogin가 예외를 발생시키면 Result로 래핑해 리턴한다`() = runTest {
        val unitTestException = Exception("unit test")
        coEvery { loginRepository.refreshFcmTokenLogin(any(), any()) } throws unitTestException

        val actual = useCase("token")

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull())
            .isNotNull
            .isEqualTo(unitTestException)
    }

    @Test
    fun `useCase는 loginRepository의 requestFcmTokenLogin 완료되면 tokenRepository의 updateThtToken를 호출한다`() = runTest {
        coEvery { tokenRepository.fetchPhone() } returns "phone"
        coEvery { loginRepository.refreshFcmTokenLogin(any(), any()) } returns FcmTokenLoginResponseModel(
            "token", 1
        )
        useCase("fcmToken")
        coVerify(exactly = 1) { tokenRepository.updateThtToken(any(), any(), any()) }
    }

    @Test
    fun `useCase는 phone과 loginRepository의 requestFcmTokenLogin의 리턴값을 tokenRepository의 updateThtToken에 전달한다`() = runTest {
        val phone = "phone"
        val token = "token"
        val tokenExpires = 1L
        coEvery { tokenRepository.fetchPhone() } returns phone
        coEvery { loginRepository.refreshFcmTokenLogin(any(), any()) } returns FcmTokenLoginResponseModel(
            token, tokenExpires
        )
        useCase("fcmToken")
        coVerify(exactly = 1) { tokenRepository.updateThtToken(token, tokenExpires, phone) }
    }

}
