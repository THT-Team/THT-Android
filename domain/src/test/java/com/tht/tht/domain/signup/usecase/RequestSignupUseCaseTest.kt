package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.SignupException
import com.tht.tht.domain.signup.model.SignupResponseModel
import com.tht.tht.domain.signup.model.SignupUserModel
import com.tht.tht.domain.signup.repository.SignupRepository
import com.tht.tht.domain.token.repository.TokenRepository
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
    private lateinit var tokenRepository: TokenRepository
    private lateinit var repository: SignupRepository
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    private val validSignupUser = SignupUserModel.getFromDefaultArgument(
        phone = "phone",
        nickname = "nickname",
        email = "email",
        gender = "gender",
        birthday = "birthday",
        interestKeys = listOf(0L, 1L, 2L),
        lat = 1.0,
        lng = 1.0,
        address = "address",
        regionCode = "regionCode",
        preferredGender = "preferredGender",
        profileImgUrl = listOf("image1", "image2"),
        introduce = "introduce",
        idealTypeKeys = listOf(0L, 1L, 2L)
    )

    @Before
    fun setupTest() {
        repository = mockk(relaxed = true)
        tokenRepository = mockk(relaxed = true)
        removeSignupUseCase = mockk(relaxed = true)
        useCase = RequestSignupUseCase(
            repository,
            tokenRepository,
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
        val nickInvalidSignupUser = validSignupUser.copy(nickname = "")
        coEvery { repository.fetchSignupUser(any()) } returns nickInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 email이 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val emailInvalidSignupUser = validSignupUser.copy(email = "")
        coEvery { repository.fetchSignupUser(any()) } returns emailInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 birthday가 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val birthInvalidSignupUser = validSignupUser.copy(gender = "")
        coEvery { repository.fetchSignupUser(any()) } returns birthInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 interestKeys가 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val interestInvalidSignupUser = validSignupUser.copy(interestKeys = emptyList())
        coEvery { repository.fetchSignupUser(any()) } returns interestInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 lat이 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val latInvalidSignupUser = validSignupUser.copy(lat = -1.0)
        coEvery { repository.fetchSignupUser(any()) } returns latInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 lng이 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val lngInvalidSignupUser = validSignupUser.copy(lng = -1.0)
        coEvery { repository.fetchSignupUser(any()) } returns lngInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 address가 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val addressInvalidSignupUser = validSignupUser.copy(address = "")
        coEvery { repository.fetchSignupUser(any()) } returns addressInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 preferredGender가 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val preferredGenderInvalidSignupUser = validSignupUser.copy(preferredGender = "")
        coEvery { repository.fetchSignupUser(any()) } returns preferredGenderInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 profileImgUrl가 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val profileImageInvalidSignupUser = validSignupUser.copy(profileImgUrl = emptyList())
        coEvery { repository.fetchSignupUser(any()) } returns profileImageInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 introduce가 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val introduceInvalidSignupUser = validSignupUser.copy(introduce = "")
        coEvery { repository.fetchSignupUser(any()) } returns introduceInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 idealTypeKeys가 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val idealInvalidSignupUser = validSignupUser.copy(idealTypeKeys = emptyList())
        coEvery { repository.fetchSignupUser(any()) } returns idealInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser의 리턴 결과의 gender이 유효하지 않다면 SignupUserInfoInvalidateException를 Reuslt로 래핑해 리턴한다`() = runTest(testDispatcher) {
        val genderInvalidSignupUser = validSignupUser.copy(birthday = "")
        coEvery { repository.fetchSignupUser(any()) } returns genderInvalidSignupUser

        val actual = useCase("test").exceptionOrNull()

        assertThat(actual)
            .isNotNull
            .isInstanceOf(SignupException.SignupUserInfoInvalidateException::class.java)
    }

    @Test
    fun `useCase는 Repository의 requestSignup가 true를 리턴하면 RemoveSignupUserUseCase를 호출한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns validSignupUser
        coEvery { tokenRepository.fetchFcmToken() } returns "token"
        coEvery { repository.requestSignup(any()) } returns mockk(relaxed = true)

        useCase("test")

        coVerify(exactly = 1) { removeSignupUseCase(any()) }
    }

    @Test
    fun `useCase는 Repository의 requestSignup의 결과의 유효성을 Result로 래핑해서 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns validSignupUser
        coEvery { tokenRepository.fetchFcmToken() } returns "token"
        coEvery { repository.requestSignup(any()) } returns SignupResponseModel(
            accessToken = "token",
            accessTokenExpiresIn = 1
        )

        val actual = useCase("test").getOrNull()

        assertThat(actual)
            .isNotNull
            .isTrue
    }

    @Test
    fun `useCase는 Repository의 requestSignup가 에러를 전달하면 Result타입으로 래핑하여 전달한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns validSignupUser
        coEvery { tokenRepository.fetchFcmToken() } returns "token"
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
        coEvery { tokenRepository.fetchFcmToken() } returns "token"
        coEvery { repository.requestSignup(any()) } returns mockk(relaxed = true)
        val actual = useCase("test")

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.getOrNull())
            .isNotNull
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser리턴값의 유효성 체크를 통과하고 fcmToken값이 유효하면 requestSignup를 호출한다`() = runTest(testDispatcher) {
        coEvery { repository.fetchSignupUser(any()) } returns validSignupUser
        coEvery { tokenRepository.fetchFcmToken() } returns "token"
        useCase("test")
        coVerify(exactly = 1) { repository.requestSignup(any()) }
    }

    @Test
    fun `useCase는 Repository의 fetchSignupUser를 호출한다`() = runTest(testDispatcher) {
        useCase("test")
        coVerify(exactly = 1) { repository.fetchSignupUser(any()) }
    }
}
