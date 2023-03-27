package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.repository.SignupRepository
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
internal class RequestPhoneVerifyUseCaseTest {

    private lateinit var useCase: RequestPhoneVerifyUseCase
    private lateinit var createSignupUserUseCase: CreateSignupUserUseCase
    private lateinit var repository: SignupRepository
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setupTest() {
        repository = mockk(relaxed = true)
        createSignupUserUseCase = mockk(relaxed = true)
        useCase = RequestPhoneVerifyUseCase(
            repository,
            createSignupUserUseCase,
            testDispatcher
        )
    }

    @Test
    fun `useCase는 Repository의 두 auth 매개변수들이 동일하고 Repository의 fetchSignupUser가 null을 리턴하면 CreateSignupUserUseCase를 호출한다`() = runTest(testDispatcher) {
        val phone = "test"
        coEvery { repository.fetchSignupUser(any()) } returns null

        useCase("123456", phone, "123456")

        coVerify { createSignupUserUseCase(phone) }
    }

    @Test
    fun `useCase는 Repository의 두 auth 매개변수들이 동일하고 Repository의 fetchSignupUser가 null을 리턴하지 않으면 CreateSignupUserUseCase를 호출하지 않는다`() = runTest(testDispatcher) {
        val phone = "test"
        coEvery { repository.fetchSignupUser(any()) } returns mockk(relaxed = true)

        useCase("123456", phone, "123456")

        coVerify(exactly = 0) { createSignupUserUseCase(phone) }
    }


    @Test
    fun `useCase는 매개변수 apiAuthNum과 inputAuthNum가 동일함을 체크하고 그 결과를 리턴한다`() = runTest(testDispatcher) {
        val actual = useCase("123456", "test", "123456").getOrNull()

        assertThat(actual)
            .isNotNull
            .isTrue
    }

    @Test
    fun `useCase는 Repository에서 에러를 전달하면 Result타입으로 래핑하여 전달한다`() = runTest(testDispatcher) {
        val expect = Exception("unit test exception")
        coEvery { repository.fetchSignupUser(any()) } throws expect

        val actual = useCase("123456","test", "123456")

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull()?.message)
            .isNotNull
            .isEqualTo(expect.message)
    }

    @Test
    fun `useCase는 결과를 Result타입으로 래핑하여 리턴한다`() = runTest(testDispatcher) {
        val actual = useCase("123456","test", "123456")

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.getOrNull())
            .isNotNull
    }
}
