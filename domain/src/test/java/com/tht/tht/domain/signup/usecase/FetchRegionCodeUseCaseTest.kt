package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.RegionCodeModel
import com.tht.tht.domain.signup.repository.RegionCodeRepository
import io.mockk.coEvery
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
internal class FetchRegionCodeUseCaseTest {

    private lateinit var useCase: FetchRegionCodeUseCase
    private lateinit var repository: RegionCodeRepository
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setupTest() {
        repository = mockk(relaxed = true)
        useCase = FetchRegionCodeUseCase(
            repository,
            testDispatcher
        )
    }

    @Test
    fun `useCase는 Repository의 fetchRegionCode의 결과를 Result로 래핑해서 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchRegionCode("test") } returns mockk()
        val actual = useCase("test")

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.getOrNull())
            .isNotNull
    }

    @Test
    fun `useCase는 Repository에서 에러를 전달하면 Result로 래핑하여 전달한다`() = runTest(testDispatcher) {
        val expect = Exception("unit test exception")
        coEvery { repository.fetchRegionCode("test") } throws expect

        val actual = useCase("test")

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull()?.message)
            .isNotNull
            .isEqualTo(expect.message)
    }

    @Test
    fun `useCase는 올바른 주소를 입력하면 결과를 RegionCodeModel을 Result로 래핑하여 전달한다`() = runTest(testDispatcher) {
        val expect = RegionCodeModel("4113300000")
        coEvery { repository.fetchRegionCode("경기도 성남시 중원구") } returns expect
        val actual = useCase("경기도 성남시 중원구")

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.getOrNull())
            .isNotNull
            .isInstanceOf(RegionCodeModel::class.java)

        assertThat(actual.getOrNull()?.regionCode)
            .isNotNull
            .isEqualTo(expect.regionCode)
    }

    @Test
    fun `useCase는 잘못된 주소를 입력하면 결과를 예외를 Result로 래핑하여 전달한다`() = runTest(testDispatcher) {
        val expect = Exception("unit test exception")
        coEvery { repository.fetchRegionCode("경기도 성남시 중원") } throws  expect
        val actual = useCase("경기도 성남시 중원")

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull()?.message)
            .isNotNull
            .isEqualTo(expect.message)
    }
}
