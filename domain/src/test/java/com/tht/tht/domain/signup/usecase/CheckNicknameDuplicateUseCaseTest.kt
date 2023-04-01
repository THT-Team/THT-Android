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
internal class CheckNicknameDuplicateUseCaseTest {

    private lateinit var useCase: CheckNicknameDuplicateUseCase
    private lateinit var repository: SignupRepository
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setupTest() {
        repository = mockk(relaxed = true)
        useCase = CheckNicknameDuplicateUseCase(
            repository,
            testDispatcher
        )
    }

    @Test
    fun `useCase는 Repository가 Exception을 발생시키면 Result로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val unitTextException = java.lang.Exception("unitTest")
        coEvery { repository.checkNicknameDuplicate(any()) } throws unitTextException
        val expect = kotlin.runCatching { throw unitTextException }
        val actual = useCase("nickname")
        assertThat(actual)
            .isEqualTo(expect)
    }

    @Test
    fun `useCase는 Repository의 checkNicknameDuplicate 결과를 Result로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val nickname = "nickname"
        coEvery { repository.checkNicknameDuplicate(nickname) } returns true
        val expect = kotlin.runCatching { true }
        val actual = useCase(nickname)
        assertThat(actual)
            .isEqualTo(expect)
    }

    @Test
    fun `useCase는 전달받은 nickname을 Repository의 checkNicknameDuplicate의 매개변수로 전달한다`() = runTest(testDispatcher) {
        val nickname = "nickname"
        useCase(nickname)
        coVerify { repository.checkNicknameDuplicate(nickname) }
    }

    @Test
    fun `useCase는 Repository의 checkNicknameDuplicate를 호출한다`() = runTest(testDispatcher) {
        useCase("nickname")
        coVerify { repository.checkNicknameDuplicate(any()) }
    }
}
