package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.SignupUserModel
import com.tht.tht.domain.signup.repository.SignupRepository
import com.tht.tht.domain.type.SignInType
import io.mockk.coEvery
import io.mockk.coVerify
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
internal class CreateSignupUserUseCaseTest {

    private lateinit var useCase: CreateSignupUserUseCase
    private lateinit var repository: SignupRepository
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setupTest() {
        repository = mockk(relaxed = true)
        useCase = CreateSignupUserUseCase(
            repository,
            testDispatcher
        )
    }

    @Test
    fun `useCase는 Repository의 saveSignupUser리턴 결과를 리턴한다`() = runTest(testDispatcher) {
        val expect = SignupUserModel.getFromDefaultArgument(phone = "test")
        coEvery { repository.saveSignupUser(any()) } returns expect

        val actual = useCase("test", SignInType.NORMAL)
        assertThat(actual)
            .isEqualTo(expect)
    }

    @Test
    fun `useCase는 SignupUserModel타입을 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.saveSignupUser(any()) } returns mockk()
        val actual = useCase("test", SignInType.NORMAL)

        assertThat(actual)
            .isInstanceOf(SignupUserModel::class.java)
    }

    @Test
    fun `useCase는 매개변수 phone과 signInType값이 적용된 SignupUserModel을 생성하여 saveSignupUser에 매개변수로 전달한다`() = runTest(testDispatcher) {
        val expect = SignupUserModel.getFromDefaultArgument(
            phone = "test", snsType = SignInType.NORMAL.key
        )
        useCase(expect.phone, SignInType.NORMAL)
        coVerify(exactly = 1) { repository.saveSignupUser(expect) }

    }

    @Test
    fun `useCase는 Repository의 saveSignupUser를 호출한다`() = runTest(testDispatcher) {
        useCase("test", SignInType.NORMAL)
        coVerify(exactly = 1) { repository.saveSignupUser(any()) }
    }
}
