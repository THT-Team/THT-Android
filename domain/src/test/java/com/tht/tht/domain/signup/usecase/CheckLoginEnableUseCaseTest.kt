package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.login.usecase.LoginUseCase
import com.tht.tht.domain.signup.model.SignupCheckModel
import com.tht.tht.domain.signup.repository.SignupRepository
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
    private lateinit var loginUseCase: LoginUseCase
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setupTest() {
        signupRepository = mockk(relaxed = true)
        loginUseCase = mockk(relaxed = true)
        useCase = CheckLoginEnableUseCase(
            signupRepository,
            loginUseCase
        )
    }

    @Test
    fun `useCase는 signupRespotiroy의 checkSignupState를 호출한다`() = runTest(testDispatcher) {
        useCase("phone")
        coVerify { signupRepository.checkSignupState(any()) }
    }

    @Test
    fun `useCase는 checkSignupState의 결과가 true이면 loginUseCase를 호출한다`() = runTest(testDispatcher) {
        coEvery { signupRepository.checkSignupState(any()) } returns SignupCheckModel(true, emptyList())
        useCase("phone")
        coVerify { loginUseCase(any()) }
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
