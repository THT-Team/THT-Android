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
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
internal class PatchSignupPreferredGenderUseCaseTest {

    private lateinit var useCase: PatchSignupPreferredGenderUseCase
    private lateinit var repository: SignupRepository
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())


    @Before
    fun setupTest() {
        repository = mockk(relaxed = true)
        useCase = PatchSignupPreferredGenderUseCase(
            repository,
            testDispatcher
        )
    }

    @Test
    fun `useCase는 매개변수 preferredGender이 유효하지 않으면 InputDataInvalidateException를 Result로 래핑하여 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns mockk()

        val actual = useCase("phone",  "")

        Assertions.assertThat(actual)
            .isInstanceOf(Result::class.java)

        Assertions.assertThat(actual.exceptionOrNull())
            .isNotNull
            .isInstanceOf(SignupException.InputDataInvalidateException::class.java)
    }

    @Test
    fun `useCase는 매개변수 phone을 Repository의 patchSignupUser의 매개변수로 호출한다`() = runTest(testDispatcher) {
        val phone = "phone"
        coEvery { repository.fetchSignupUser(any()) } returns mockk(relaxed = true)

        useCase(phone,  "preferredGender")
        coVerify(exactly = 1) { repository.patchSignupUser(phone, any()) }
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser결과의 preferredGender를 변경하여 patchSignupUser의 매개변수로 호출한다`() = runTest(testDispatcher) {
        val preferredGender = "preferredGender"
        val signupUser = SignupUserModel.getFromDefaultArgument()
        coEvery { repository.fetchSignupUser(any()) } returns signupUser
        val expect = signupUser.copy(preferredGender = preferredGender)

        useCase("phone", preferredGender)
        coVerify(exactly = 1) { repository.patchSignupUser(any(), expect) }
    }

    @Test
    fun `useCase는 Repository의 patchSignupUser의 결과를 Result로 래핑해서 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns mockk(relaxed = true)
        coEvery { repository.patchSignupUser(any(), any()) } returns true

        val actual = useCase("test",  "preferredGender").getOrNull()
        Assertions.assertThat(actual)
            .isNotNull
            .isTrue
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser결과가 null이면 SignupUserInvalidateException를 Result로 래핑하여 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns null
        useCase("test",  "preferredGender")

        val actual = useCase("test",  "preferredGender")

        Assertions.assertThat(actual)
            .isInstanceOf(Result::class.java)

        Assertions.assertThat(actual.exceptionOrNull())
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 patchSignupUser에서 에러를 전달하면 Result타입으로 래핑하여 전달한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns mockk(relaxed = true)
        val expect = Exception("unit test exception")
        coEvery { repository.patchSignupUser(any(), any()) } throws expect

        val actual = useCase("test",  "preferredGender")

        Assertions.assertThat(actual)
            .isInstanceOf(Result::class.java)

        Assertions.assertThat(actual.exceptionOrNull()?.message)
            .isNotNull
            .isEqualTo(expect.message)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser에서 에러를 전달하면 Result타입으로 래핑하여 전달한다`() = runTest(testDispatcher) {
        val expect = Exception("unit test exception")
        coEvery { repository.fetchSignupUser(any()) } throws expect

        val actual = useCase("test",  "preferredGender")

        Assertions.assertThat(actual)
            .isInstanceOf(Result::class.java)

        Assertions.assertThat(actual.exceptionOrNull()?.message)
            .isNotNull
            .isEqualTo(expect.message)
    }

    @Test
    fun `useCase는 결과를 Result타입으로 래핑하여 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns mockk(relaxed = true)
        coEvery { repository.patchSignupUser(any(), any()) } returns true
        val actual = useCase("test",  "preferredGender")

        Assertions.assertThat(actual)
            .isInstanceOf(Result::class.java)

        Assertions.assertThat(actual.getOrNull())
            .isNotNull
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser결과가 null이 아니면 patchSignupUser를 호출한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns mockk(relaxed = true)
        useCase("test",  "preferredGender")
        coVerify(exactly = 1) { repository.patchSignupUser(any(), any()) }
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser를 호출한다`() = runTest(testDispatcher) {
        useCase("test", "preferredGender")
        coVerify(exactly = 1) { repository.fetchSignupUser(any()) }
    }

}
