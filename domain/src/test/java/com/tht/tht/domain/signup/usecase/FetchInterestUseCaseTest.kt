package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.InterestModel
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
internal class FetchInterestUseCaseTest {

    private lateinit var useCase: FetchInterestUseCase
    private lateinit var repository: SignupRepository
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setupTest() {
        repository = mockk(relaxed = true)
        useCase = FetchInterestUseCase(
            repository,
            testDispatcher
        )
    }

    @Test
    fun `useCase는 Repository의 fetchInterest의 결과를 Result로 래핑해서 리턴한다`() = runTest(testDispatcher) {
        val expect: List<InterestModel> = mockk()
        coEvery { repository.fetchInterest() } returns expect

        val actual = useCase().getOrNull()

        assertThat(actual)
            .isNotNull
            .isEqualTo(expect)
    }

    @Test
    fun `useCase는 Repository에서 에러를 전달하면 Result타입으로 래핑하여 전달한다`() = runTest(testDispatcher) {
        val expect = Exception("unit test exception")
        coEvery { repository.fetchInterest() } throws expect

        val actual = useCase()

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull()?.message)
            .isNotNull
            .isEqualTo(expect.message)
    }

    @Test
    fun `useCase는 결과를 Result타입으로 래핑하여 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchInterest() } returns mockk()
        val actual = useCase()

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.getOrNull())
            .isNotNull
    }

    @Test
    fun `useCase는 Repository의 fetchInterest를 호출한다`() = runTest(testDispatcher) {
        useCase()
        coVerify(exactly = 1) { repository.fetchInterest() }
    }
}
