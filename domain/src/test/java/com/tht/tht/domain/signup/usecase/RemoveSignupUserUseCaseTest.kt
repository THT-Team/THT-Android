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
internal class RemoveSignupUserUseCaseTest {

    private lateinit var useCase: RemoveSignupUserUseCase
    private lateinit var repository: SignupRepository
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setupTest() {
        repository = mockk(relaxed = true)
        useCase = RemoveSignupUserUseCase(
            repository,
            testDispatcher
        )
    }

    @Test
    fun `useCase는 Repository의 removeSignupUser리턴 결과를 리턴한다`() = runTest(testDispatcher) {
        val expect = true
        coEvery { repository.removeSignupUser(any()) } returns true

        val actual = useCase("test")
        assertThat(actual)
            .isEqualTo(expect)
    }

    @Test
    fun `useCase는 Boolean타입을 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.removeSignupUser(any()) } returns false
        val actual = useCase("test")

        assertThat(actual)
            .isInstanceOf(java.lang.Boolean::class.java)
    }

    @Test
    fun `useCase는 Repository의 removeSignupUser를 호출한다`() = runTest(testDispatcher) {
        useCase("")
        coVerify(exactly = 1) { repository.removeSignupUser(any()) }
    }
}
