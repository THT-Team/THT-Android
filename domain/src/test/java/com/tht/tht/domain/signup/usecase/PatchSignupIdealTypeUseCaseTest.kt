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
internal class PatchSignupIdealTypeUseCaseTest {

    private lateinit var useCase: PatchSignupIdealTypeUseCase
    private lateinit var repository: SignupRepository
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    private val validIdealTypeKey = listOf(0L, 1L, 2L)
    private val invalidIdealTypeKey = emptyList<Long>()


    @Before
    fun setupTest() {
        repository = mockk(relaxed = true)
        useCase = PatchSignupIdealTypeUseCase(
            repository,
            testDispatcher
        )
    }

    @Test
    fun `useCase는 매개변수 idealTypeKeys가 유효하지 않으면 IdealRequireSizeException를 Result로 래핑하여 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns mockk()
        val actual = useCase("phone", invalidIdealTypeKey)

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull())
            .isNotNull
            .isInstanceOf(SignupException.InputDataRequireSizeException::class.java)
    }

    @Test
    fun `useCase는 매개변수 phone을 Repository의 patchSignupUser의 매개변수로 호출한다`() = runTest(testDispatcher) {
        val phone = "phone"
        coEvery { repository.fetchSignupUser(any()) } returns mockk(relaxed = true)

        useCase(phone, validIdealTypeKey)
        coVerify(exactly = 1) { repository.patchSignupUser(phone, any()) }
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser결과의 idealTypeKeys를 변경하여 patchSignupUser의 매개변수로 호출한다`() = runTest(testDispatcher) {
        val signupUser = SignupUserModel.getFromDefaultArgument()
        coEvery { repository.fetchSignupUser(any()) } returns signupUser
        val expect = signupUser.copy(idealTypeKeys = validIdealTypeKey)

        useCase("phone", validIdealTypeKey)
        coVerify(exactly = 1) { repository.patchSignupUser(any(), expect) }
    }

    @Test
    fun `useCase는 Repository의 patchSignupUser의 결과를 Result로 래핑해서 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns mockk(relaxed = true)
        coEvery { repository.patchSignupUser(any(), any()) } returns true

        val actual = useCase("test", validIdealTypeKey).getOrNull()

        assertThat(actual)
            .isNotNull
            .isTrue
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser결과가 null이면 SignupUserInvalidateException를 Result로 래핑하여 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns null
        useCase("test", validIdealTypeKey)

        val actual = useCase("test", validIdealTypeKey)

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull())
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 patchSignupUser에서 에러를 전달하면 Result타입으로 래핑하여 전달한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns mockk(relaxed = true)
        val expect = Exception("unit test exception")
        coEvery { repository.patchSignupUser(any(), any()) } throws expect

        val actual = useCase("test", validIdealTypeKey)

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull()?.message)
            .isNotNull
            .isEqualTo(expect.message)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser에서 에러를 전달하면 Result타입으로 래핑하여 전달한다`() = runTest(testDispatcher) {
        val expect = Exception("unit test exception")
        coEvery { repository.fetchSignupUser(any()) } throws expect

        val actual = useCase("test", validIdealTypeKey)

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull()?.message)
            .isNotNull
            .isEqualTo(expect.message)
    }

    @Test
    fun `useCase는 결과를 Result타입으로 래핑하여 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns mockk(relaxed = true)
        coEvery { repository.patchSignupUser(any(), any()) } returns true
        val actual = useCase("test", validIdealTypeKey)

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.getOrNull())
            .isNotNull
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser결과가 null이 아니면 patchSignupUser를 호출한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns mockk(relaxed = true)
        useCase("test", validIdealTypeKey)
        coVerify(exactly = 1) { repository.patchSignupUser(any(), any()) }
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser를 호출한다`() = runTest(testDispatcher) {
        useCase("test", validIdealTypeKey)
        coVerify(exactly = 1) { repository.fetchSignupUser(any()) }
    }



}
