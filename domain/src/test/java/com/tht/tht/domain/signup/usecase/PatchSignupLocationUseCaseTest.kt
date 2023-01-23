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
internal class PatchSignupLocationUseCaseTest {

    private lateinit var useCase: PatchSignupLocationUseCase
    private lateinit var repository: SignupRepository
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())


    @Before
    fun setupTest() {
        repository = mockk(relaxed = true)
        useCase = PatchSignupLocationUseCase(
            repository,
            testDispatcher
        )
    }

    @Test
    fun `useCase는 매개변수 address가 유효하지 않으면 InputDataInvalidateException를 Result로 래핑하여 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns mockk()

        val actual = useCase("phone",  0.0, 0.0, "")

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull())
            .isNotNull
            .isInstanceOf(SignupException.InputDataInvalidateException::class.java)
    }

    @Test
    fun `useCase는 매개변수 lng이 유효하지 않으면 InputDataInvalidateException를 Result로 래핑하여 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns mockk()

        val actual = useCase("phone",  0.0, -1.0, "address")

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull())
            .isNotNull
            .isInstanceOf(SignupException.InputDataInvalidateException::class.java)
    }

    @Test
    fun `useCase는 매개변수 lat이 유효하지 않으면 InputDataInvalidateException를 Result로 래핑하여 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns mockk()

        val actual = useCase("phone",  -1.0, 0.0, "address")

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull())
            .isNotNull
            .isInstanceOf(SignupException.InputDataInvalidateException::class.java)
    }

    @Test
    fun `useCase는 매개변수 phone을 Repository의 patchSignupUser의 매개변수로 호출한다`() = runTest(testDispatcher) {
        val phone = "phone"
        coEvery { repository.fetchSignupUser(any()) } returns mockk(relaxed = true)

        useCase(phone,  0.0, 0.0, "address")
        coVerify(exactly = 1) { repository.patchSignupUser(phone, any()) }
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser결과의 location정보를 변경하여 patchSignupUser의 매개변수로 호출한다`() = runTest(testDispatcher) {
        val (lat, lng, address) = Triple( 0.0, 0.0, "address")
        val signupUser = SignupUserModel.getFromDefaultArgument()
        coEvery { repository.fetchSignupUser(any()) } returns signupUser
        val expect = signupUser.copy(lat = lat, lng = lng, address = address)

        useCase("phone", lat, lng, address)
        coVerify(exactly = 1) { repository.patchSignupUser(any(), expect) }
    }

    @Test
    fun `useCase는 Repository의 patchSignupUser의 결과를 Result로 래핑해서 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns mockk(relaxed = true)
        coEvery { repository.patchSignupUser(any(), any()) } returns true

        val actual = useCase("test",  0.0, 0.0, "address").getOrNull()

        assertThat(actual)
            .isNotNull
            .isTrue
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser결과가 null이면 SignupUserInvalidateException를 Result로 래핑하여 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns null
        useCase("test",  0.0, 0.0, "address")

        val actual = useCase("test",  0.0, 0.0, "address")

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

        val actual = useCase("test",  0.0, 0.0, "address")

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

        val actual = useCase("test",  0.0, 0.0, "address")

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
        val actual = useCase("test",  0.0, 0.0, "address")

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.getOrNull())
            .isNotNull
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser결과가 null이 아니면 patchSignupUser를 호출한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns mockk(relaxed = true)
        useCase("test",  0.0, 0.0, "address")
        coVerify(exactly = 1) { repository.patchSignupUser(any(), any()) }
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser를 호출한다`() = runTest(testDispatcher) {
        useCase("test", 0.0, 0.0, "address")
        coVerify(exactly = 1) { repository.fetchSignupUser(any()) }
    }

}
