package com.tht.tht.domain.signup.usecase

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
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
internal class PatchSignupDataUseCaseTest {

    private lateinit var useCase: PatchSignupDataUseCase
    private lateinit var repository: SignupRepository
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setupTest() {
        repository = mockk(relaxed = true)
        useCase = PatchSignupDataUseCase(
            repository,
            testDispatcher
        )
    }

    @Test
    fun `useCase는 repository의 fetchSignupUser를 호출한다`() = runTest(testDispatcher) {
        useCase("phone") { it }
        coVerify(exactly = 1) { repository.fetchSignupUser(any()) }
    }

    @Test
    fun `useCase는 repository의 fetchSignupUser가 예외를 발생시키면 Result로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val unitTestException = Exception("unit test")
        coEvery { repository.fetchSignupUser(any()) } throws unitTestException

        val actual = useCase("phone") { it }

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull()?.message)
            .isNotNull
            .isEqualTo(unitTestException.message)
    }

    @Test
    fun `useCase는 repository의 fetchSignupUser가 null을 리턴하면 SignupUserInvalidateException를 Result로 래핑해 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns null

        val actual = useCase("phone") { it }

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull())
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInvalidateException::class.java)
    }

    @Test
    fun `useCase는 fetchSignupUser리턴 값이 유효하면 매개변수 reduce의 매개변수로 전달한다`() = runTest(testDispatcher) {
        var reduceArgumentHashCode: Int = -1
        val reduce: (SignupUserModel) -> SignupUserModel = {
            reduceArgumentHashCode = it.hashCode()
            it
        }
        val expect = SignupUserModel.getFromDefaultArgument()
        coEvery { repository.fetchSignupUser(any()) } returns expect

        useCase("phone", reduce)

        assertThat(reduceArgumentHashCode)
            .isNotEqualTo(-1)
            .isEqualTo(expect.hashCode())
    }

    @Test
    fun `useCase는 fetchSignupUser리턴 값이 유효하면 patchSignupUser를 호출한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns SignupUserModel.getFromDefaultArgument()
        useCase("phone") { it }
        coVerify { repository.patchSignupUser(any(), any()) }
    }

    @Test
    fun `useCase는 매개변수 phone과 reduce의 실행 결과를 patchSignupUser의 매개변수로 전달한다`() = runTest(testDispatcher) {
        val phone = "phone"
        val reduce: (SignupUserModel) -> SignupUserModel = {
            it.copy(gender = "gender reduce")
        }
        val expect = SignupUserModel.getFromDefaultArgument()
        coEvery { repository.fetchSignupUser(any()) } returns expect
        useCase(phone, reduce)
        coVerify { repository.patchSignupUser(phone, reduce(expect)) }
    }

    @Test
    fun `useCase는 repository의 patchSignupUser가 예외를 발생시키면 Result로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val unitTestException = Exception("unit test")
        coEvery { repository.patchSignupUser(any(), any()) } throws unitTestException
        coEvery { repository.fetchSignupUser(any()) } returns SignupUserModel.getFromDefaultArgument()

        val actual = useCase("phone") { it }

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull()?.message)
            .isNotNull
            .isEqualTo(unitTestException.message)
    }

    @Test
    fun `useCase는 repository의 patchSignupUser의 결과를 Result로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val expect = true
        coEvery { repository.patchSignupUser(any(), any()) } returns expect
        coEvery { repository.fetchSignupUser(any()) } returns SignupUserModel.getFromDefaultArgument()

        val actual = useCase("phone") { it }

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.getOrNull())
            .isNotNull
            .isEqualTo(expect)

    }
}
