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
internal class RequestVerifyUseCaseTest {

    private lateinit var useCase: RequestVerifyUseCase
    private lateinit var createSignupUserUseCase: CreateSignupUserUseCase
    private lateinit var repository: SignupRepository
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setupTest() {
        repository = mockk(relaxed = true)
        createSignupUserUseCase = mockk(relaxed = true)
        useCase = RequestVerifyUseCase(
            repository,
            createSignupUserUseCase,
            testDispatcher
        )
    }

    @Test
    fun `useCase는 Repository의 requestVerify가 true를 리턴하고 Repository의 fetchSignupUser가 null을 리턴하면 CreateSignupUserUseCase를 호출한다`() = runTest(testDispatcher) {
        val phone = "test"
        coEvery { repository.fetchSignupUser(any()) } returns null
        coEvery { repository.requestVerify(any(), any()) } returns true

        useCase(phone, "auth")

        coVerify { createSignupUserUseCase(phone) }
    }

    @Test
    fun `useCase는 Repository의 requestVerify가 true를 리턴하고 Repository의 fetchSignupUser가 null을 리턴하지 않으면 CreateSignupUserUseCase를 호출하지 않는다`() = runTest(testDispatcher) {
        val phone = "test"
        coEvery { repository.fetchSignupUser(any()) } returns mockk(relaxed = true)
        coEvery { repository.requestVerify(any(), any()) } returns true

        useCase(phone, "auth")

        coVerify(exactly = 0) { createSignupUserUseCase(phone) }
    }


    @Test
    fun `useCase는 Repository의 requestVerify의 결과를 Result로 래핑해서 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.requestVerify(any(), any()) } returns true

        val actual = useCase("test", "auth").getOrNull()

        assertThat(actual)
            .isNotNull
            .isTrue
    }

    @Test
    fun `useCase는 Repository에서 에러를 전달하면 Result타입으로 래핑하여 전달한다`() = runTest(testDispatcher) {
        val expect = Exception("unit test exception")
        coEvery { repository.requestVerify(any(), any()) } throws expect

        val actual = useCase("test", "auth")

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull()?.message)
            .isNotNull
            .isEqualTo(expect.message)
    }

    @Test
    fun `useCase는 결과를 Result타입으로 래핑하여 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.requestVerify(any(), any()) } returns true
        val actual = useCase("test", "auth")

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.getOrNull())
            .isNotNull
    }

    @Test
    fun `useCase는 Repository의 requestVerify를 호출한다`() = runTest(testDispatcher) {
        useCase("test", "auth")
        coVerify(exactly = 1) { repository.requestVerify(any(), any()) }
    }

}
