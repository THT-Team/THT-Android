package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.login.repository.LoginRepository
import com.tht.tht.domain.signup.model.SignupCheckModel
import com.tht.tht.domain.signup.repository.SignupRepository
import com.tht.tht.domain.token.model.FcmTokenLoginResponseModel
import com.tht.tht.domain.token.repository.TokenRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.Before
import org.junit.Test

@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
internal class CheckLoginEnableUseCaseTest {
    private lateinit var useCase: CheckLoginEnableUseCase
    private lateinit var signupRepository: SignupRepository
    private lateinit var tokenRepository: TokenRepository
    private lateinit var loginRepository: LoginRepository
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setupTest() {
        signupRepository = mockk(relaxed = true)
        tokenRepository = mockk(relaxed = true)
        loginRepository = mockk(relaxed = true)
        useCase = CheckLoginEnableUseCase(
            signupRepository,
            tokenRepository,
            loginRepository
        )
    }

    @Test
    fun `useCase는 signupRespotiroy의 checkSignupState를 호출한다`() = runTest(testDispatcher) {
        useCase("phone")
        coVerify { signupRepository.checkSignupState(any()) }
    }

    @Test
    fun `useCase는 signupRespotiroy의 checkSignupState의 결과의 isSignup이 true라면 tokenRepository의 fetchFcmToken를 호출한다`() = runTest(testDispatcher) {
        coEvery { signupRepository.checkSignupState(any()) } returns SignupCheckModel(true, emptyList())
        useCase("phone")
        coVerify { tokenRepository.fetchFcmToken() }
    }

    @Test
    fun `useCase는 checkSignupState의 결과가 true고 fetchFcmToken의 결과가 null이 아니면 loginRepository의 requestFcmTokenLogin를 호출한다`() = runTest(testDispatcher) {
        coEvery { signupRepository.checkSignupState(any()) } returns SignupCheckModel(true, emptyList())
        coEvery { tokenRepository.fetchFcmToken() } returns "token"
        useCase("phone")
        coVerify { loginRepository.refreshFcmTokenLogin(any(), any()) }
    }

    @Test
    fun `useCase는 checkSignupState의 결과가 true고 fetchFcmToken의 결과가 null이 아니면 tokenRepository의 updateThtToken를 호출한다`() = runTest(testDispatcher) {
        coEvery { signupRepository.checkSignupState(any()) } returns SignupCheckModel(true, emptyList())
        coEvery { tokenRepository.fetchFcmToken() } returns "token"
        useCase("phone")
        coVerify { tokenRepository.updateThtToken(any(), any(), any()) }
    }

    @Test
    fun `useCase는 checkSignupState의 isSignup이 true라면 fetchFcmToken와 매개변수 phone을 매개변수로 requestFcmTokenLogin를 호출한다`() = runTest(testDispatcher) {
        val phone = "phone"
        val token = "token"
        coEvery { signupRepository.checkSignupState(phone) } returns SignupCheckModel(true, emptyList())
        coEvery { tokenRepository.fetchFcmToken() } returns token
        useCase(phone)
        coVerify { loginRepository.refreshFcmTokenLogin(token, phone) }
    }

    @Test
    fun `useCase는 checkSignupState의 isSignup이 true라면 매개변수 phone과 requestFcmTokenLogin의 결과인 token과 accessTokenExpiresIn로 updateThtToken를 호출한다`() = runTest(testDispatcher) {
        val phone = "phone"
        val fcmToken = "fcmToken"
        val token = "token"
        val accessTokenExpiresIn = 1L
        coEvery { signupRepository.checkSignupState(phone) } returns SignupCheckModel(true, emptyList())
        coEvery { tokenRepository.fetchFcmToken() } returns fcmToken
        coEvery { loginRepository.refreshFcmTokenLogin(fcmToken, phone) } returns FcmTokenLoginResponseModel(token, accessTokenExpiresIn)
        useCase(phone)
        coVerify { tokenRepository.updateThtToken(token, accessTokenExpiresIn, phone) }
    }

    @Test
    fun `useCase는 signupRespotiroy의 checkSignupState결과의 isSignup을 Result로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val expect = SignupCheckModel(true, emptyList())
        coEvery { signupRepository.checkSignupState(any()) } returns expect
        val actual = useCase("phone").getOrNull()
        assertThat(actual)
            .isNotNull
            .isEqualTo(expect.isSignup)
    }

    @Test
    fun `useCase는 실행 중 예외가 발생하면 Result로 래핑해 리턴한다`() {
        val unitTestException = Exception("UnitTest")
        coEvery { signupRepository.checkSignupState(any()) } throws unitTestException
        val actual = catchThrowable {
            runTest(testDispatcher) {
                useCase("phone").getOrThrow()
            }
        }
        assertThat(actual)
            .isInstanceOf(Exception::class.java)
            .isEqualTo(unitTestException)
    }
}
