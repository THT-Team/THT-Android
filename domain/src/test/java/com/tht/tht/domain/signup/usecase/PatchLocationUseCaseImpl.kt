package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.RegionCodeModel
import com.tht.tht.domain.signup.model.SignupException
import com.tht.tht.domain.signup.model.SignupUserModel
import com.tht.tht.domain.signup.repository.SignupRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
internal class PatchLocationUseCaseImpl {

    private lateinit var fetchRegionCodeUseCase: FetchRegionCodeUseCase
    private lateinit var patchLocationUseCase: PatchLocationUseCase
    private lateinit var repository: SignupRepository
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setUpTest() {
        repository = mockk(relaxed = true)
        fetchRegionCodeUseCase = mockk(relaxed = true)
        patchLocationUseCase = PatchLocationUseCase(
            fetchRegionCodeUseCase,
            repository,
            testDispatcher
        )
    }

    @Test
    fun `useCase는 FetchRegionCodeUseCase의 invoke()를 호출한다`() = runTest(testDispatcher) {
        patchLocationUseCase("phone", 0.0, 0.0, "")
        coVerify(exactly = 1) { fetchRegionCodeUseCase(any()) }
    }

    @Test
    fun `useCase는 FetchRegionCodeUseCase의 invoke()가 예외를 발생시키면 Result로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val unitTestException = Exception("unit test")
        coEvery { fetchRegionCodeUseCase(any()) } throws unitTestException

        val actual = patchLocationUseCase("phone", 0.0, 0.0, "")

        Assertions.assertThat(actual)
            .isInstanceOf(Result::class.java)

        Assertions.assertThat(actual.exceptionOrNull()?.message)
            .isNotNull
            .isEqualTo(unitTestException.message)
    }

    @Test
    fun `useCase는 repository의 fetchSignupUser를 호출한다`() = runTest(testDispatcher) {
        patchLocationUseCase("phone", 0.0, 0.0, "")
        coEvery { fetchRegionCodeUseCase(any()) } returns kotlin.runCatching { RegionCodeModel("0") }
        coVerify(exactly = 1) { fetchRegionCodeUseCase(any()) }
    }

    @Test
    fun `useCase는 repository의 fetchSignupUser가 예외를 발생시키면 Result로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val unitTestException = Exception("unit test")
        coEvery { fetchRegionCodeUseCase(any()) } returns kotlin.runCatching { RegionCodeModel("0") }
        coEvery { repository.fetchSignupUser(any()) } throws unitTestException

        val actual = patchLocationUseCase("phone", 0.0, 0.0, "")

        Assertions.assertThat(actual)
            .isInstanceOf(Result::class.java)

        Assertions.assertThat(actual.exceptionOrNull()?.message)
            .isNotNull
            .isEqualTo(unitTestException.message)
    }

    @Test
    fun `useCase는 repository의 fetchSignupUser가 null을 리턴하면 SignupUserInvalidateException를 Result로 래핑해 리턴한다`() = runTest(testDispatcher) {
        coEvery { fetchRegionCodeUseCase(any()) } returns kotlin.runCatching {
            RegionCodeModel("0")
        }
        coEvery { repository.fetchSignupUser(any()) } returns null

        val actual = patchLocationUseCase("phone", 0.0, 0.0, "")

        Assertions.assertThat(actual)
            .isInstanceOf(Result::class.java)

        Assertions.assertThat(actual.exceptionOrNull())
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInvalidateException::class.java)
    }

    @Test
    fun `useCase는 fetchSignupUser리턴 값이 유효하면 patchSignupUser를 호출한다`() = runTest(testDispatcher) {
        coEvery { fetchRegionCodeUseCase(any()) } returns kotlin.runCatching { RegionCodeModel("0") }
        coEvery { repository.fetchSignupUser(any()) } returns SignupUserModel.getFromDefaultArgument()
        patchLocationUseCase("phone", 0.0, 0.0, "")
        coVerify { repository.patchSignupUser(any(), any()) }
    }

    @Test
    fun `useCase는 repository의 patchSignupUser가 예외를 발생시키면 Result로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val unitTestException = Exception("unit test")
        coEvery { repository.patchSignupUser(any(), any()) } throws unitTestException
        coEvery { repository.fetchSignupUser(any()) } returns SignupUserModel.getFromDefaultArgument()
        coEvery { fetchRegionCodeUseCase(any()) } returns kotlin.runCatching { RegionCodeModel("0") }
        val actual = patchLocationUseCase("phone", 0.0, 0.0, "")

        Assertions.assertThat(actual)
            .isInstanceOf(Result::class.java)

        Assertions.assertThat(actual.exceptionOrNull()?.message)
            .isNotNull
            .isEqualTo(unitTestException.message)
    }

    @Test
    fun `useCase는 repository의 patchSignupUser의 결과를 Result로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val expect = true
        coEvery { repository.patchSignupUser(any(), any()) } returns expect
        coEvery { repository.fetchSignupUser(any()) } returns SignupUserModel.getFromDefaultArgument()
        coEvery { fetchRegionCodeUseCase(any()) } returns kotlin.runCatching { RegionCodeModel("0") }
        val actual = patchLocationUseCase("phone", 0.0, 0.0, "")

        Assertions.assertThat(actual)
            .isInstanceOf(Result::class.java)

        Assertions.assertThat(actual.getOrNull())
            .isNotNull
            .isEqualTo(expect)

    }
}
