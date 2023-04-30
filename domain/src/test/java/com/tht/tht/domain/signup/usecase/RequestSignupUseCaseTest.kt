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
internal class RequestSignupUseCaseTest {

    private lateinit var useCase: RequestSignupUseCase
    private lateinit var removeSignupUseCase: RemoveSignupUserUseCase
    private lateinit var repository: SignupRepository
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    private val validSignupUser = SignupUserModel.getFromDefaultArgument(
        phone = "phone",
        nickname = "nickname",
        email = "email",
        gender = "gender",
        birthday = "birthday",
        interestKeys = listOf("key1", "key2", "key3"),
        lat = 1.0,
        lng = 1.0,
        address = "address",
        preferredGender = "preferredGender",
        profileImgUrl = listOf("image1", "image2"),
        introduce = "introduce",
        idealTypeKeys = listOf(0L, 1L, 2L)
    )

    @Before
    fun setupTest() {
        repository = mockk(relaxed = true)
        removeSignupUseCase = mockk(relaxed = true)
        useCase = RequestSignupUseCase(
            repository,
            removeSignupUseCase,
            testDispatcher
        )
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 phone이 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val phoneInvalidSignupUser = validSignupUser.copy(phone = "")
        coEvery { repository.fetchSignupUser(any()) } returns phoneInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 nickname이 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val phoneInvalidSignupUser = validSignupUser.copy(nickname = "")
        coEvery { repository.fetchSignupUser(any()) } returns phoneInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 email이 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val phoneInvalidSignupUser = validSignupUser.copy(email = "")
        coEvery { repository.fetchSignupUser(any()) } returns phoneInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 birthday가 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val phoneInvalidSignupUser = validSignupUser.copy(gender = "")
        coEvery { repository.fetchSignupUser(any()) } returns phoneInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 interestKeys가 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val phoneInvalidSignupUser = validSignupUser.copy(interestKeys = emptyList())
        coEvery { repository.fetchSignupUser(any()) } returns phoneInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 lat이 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val phoneInvalidSignupUser = validSignupUser.copy(lat = -1.0)
        coEvery { repository.fetchSignupUser(any()) } returns phoneInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 lng이 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val phoneInvalidSignupUser = validSignupUser.copy(lng = -1.0)
        coEvery { repository.fetchSignupUser(any()) } returns phoneInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 address가 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val phoneInvalidSignupUser = validSignupUser.copy(address = "")
        coEvery { repository.fetchSignupUser(any()) } returns phoneInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 preferredGender가 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val phoneInvalidSignupUser = validSignupUser.copy(preferredGender = "")
        coEvery { repository.fetchSignupUser(any()) } returns phoneInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 profileImgUrl가 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val phoneInvalidSignupUser = validSignupUser.copy(profileImgUrl = emptyList())
        coEvery { repository.fetchSignupUser(any()) } returns phoneInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 introduce가 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val phoneInvalidSignupUser = validSignupUser.copy(introduce = "")
        coEvery { repository.fetchSignupUser(any()) } returns phoneInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 idealTypeKeys가 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val phoneInvalidSignupUser = validSignupUser.copy(idealTypeKeys = emptyList())
        coEvery { repository.fetchSignupUser(any()) } returns phoneInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 gender이 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val phoneInvalidSignupUser = validSignupUser.copy(birthday = "")
        coEvery { repository.fetchSignupUser(any()) } returns phoneInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 requestSignup가 true를 리턴하면 RemoveSignupUserUseCase를 호출한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns validSignupUser
        coEvery { repository.requestSignup(any()) } returns true

        useCase("test")

        coVerify(exactly = 1) { removeSignupUseCase(any()) }
    }

    @Test
    fun `useCase는 Repository의 requestSignup의 결과를 Result로 래핑해서 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns validSignupUser
        coEvery { repository.requestSignup(any()) } returns true

        val actual = useCase("test").getOrNull()

        assertThat(actual)
            .isNotNull
            .isTrue
    }

    @Test
    fun `useCase는 Repository의 requestSignup가 에러를 전달하면 Result타입으로 래핑하여 전달한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns validSignupUser
        val expect = Exception("unit test exception")
        coEvery { repository.requestSignup(any()) } throws expect

        val actual = useCase("test")

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull()?.message)
            .isNotNull
            .isEqualTo(expect.message)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser가 에러를 전달하면 Result타입으로 래핑하여 전달한다`() = runTest(testDispatcher) {
        val expect = Exception("unit test exception")
        coEvery { repository.fetchSignupUser(any()) } throws expect

        val actual = useCase("test")

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull()?.message)
            .isNotNull
            .isEqualTo(expect.message)
    }

    @Test
    fun `useCase는 결과를 Result타입으로 래핑하여 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns validSignupUser
        coEvery { repository.requestSignup(any()) } returns true
        val actual = useCase("test")

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.getOrNull())
            .isNotNull
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser리턴값의 유효성 체크를 통과하면 requestSignup를 호출한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns validSignupUser
        useCase("test")
        coVerify(exactly = 1) { repository.requestSignup(any()) }
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser를 호출한다`() = runTest(testDispatcher) {
        useCase("test")
        coVerify(exactly = 1) { repository.fetchSignupUser(any()) }
    }
}
