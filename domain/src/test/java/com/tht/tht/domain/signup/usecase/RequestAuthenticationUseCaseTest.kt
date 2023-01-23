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
internal class RequestAuthenticationUseCaseTest {

    private lateinit var useCase: RequestAuthenticationUseCase
    private lateinit var repository: SignupRepository
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setupTest() {
        repository = mockk(relaxed = true)
        useCase = RequestAuthenticationUseCase(
            repository,
            testDispatcher
        )
    }

    @Test
    fun `useCase는 Repository의 fetchIdealType의 결과를 Result로 래핑해서 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.requestAuthentication(any()) } returns true

        val actual = useCase("test").getOrNull()

        assertThat(actual)
            .isNotNull
            .isTrue
    }

    @Test
    fun `useCase는 Repository에서 에러를 전달하면 Result타입으로 래핑하여 전달한다`() = runTest(testDispatcher) {
        val expect = Exception("unit test exception")
        coEvery { repository.requestAuthentication(any()) } throws expect

        val actual = useCase("test")

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull()?.message)
            .isNotNull
            .isEqualTo(expect.message)
    }

    @Test
    fun `useCase는 결과를 Result타입으로 래핑하여 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.requestAuthentication(any()) } returns true
        val actual = useCase("test")

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.getOrNull())
            .isNotNull
    }

    @Test
    fun `useCase는 Repository의 requestAuthentication를 호출한다`() = runTest(testDispatcher) {
        useCase("test")
        coVerify(exactly = 1) { repository.requestAuthentication("test") }
    }

}
