package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.LocationModel
import com.tht.tht.domain.signup.repository.LocationRepository
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
internal class FetchCurrentLocationUseCaseTest {

    private lateinit var useCase: FetchCurrentLocationUseCase
    private lateinit var repository: LocationRepository
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = FetchCurrentLocationUseCase(
            repository,
            testDispatcher
        )
    }

    @Test
    fun `useCase는 Repository의 fetchCurrentLocation 결과를 Result로 래핑해서 리턴한다`() = runTest(testDispatcher) {
        val expect = mockk<LocationModel>(relaxed = true)
        coEvery { repository.fetchCurrentLocation() } returns expect

        val actual = useCase().getOrNull()

        assertThat(actual)
            .isNotNull
            .isEqualTo(expect)
    }

    @Test
    fun `useCase는 Repository에서 에러를 전달하면 Result타입으로 래핑하여 전달한다`() = runTest(testDispatcher) {
        val expect = Exception("unit test exception")
        coEvery { repository.fetchCurrentLocation() } throws expect

        val actual = useCase()

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull()?.message)
            .isNotNull
            .isEqualTo(expect.message)
    }

    @Test
    fun `useCase는 Repository의 fetchCurrentLocation을 호출한다`() = runTest(testDispatcher) {
        useCase()
        coVerify(exactly = 1) { repository.fetchCurrentLocation() }
    }
}
