package com.tht.tht.data.repository

import com.tht.tht.data.datasource.SignupApiDataSource
import com.tht.tht.data.datasource.SignupUserDataSource
import com.tht.tht.data.datasource.TermsDataSource
import com.tht.tht.data.local.entity.SignupUserEntity
import com.tht.tht.data.local.entity.TermsEntity
import com.tht.tht.data.local.mapper.toEntity
import com.tht.tht.data.local.mapper.toModel
import com.tht.tht.data.remote.mapper.toModel
import com.tht.tht.data.remote.response.ideal.IdealTypeResponse
import com.tht.tht.data.remote.response.interests.InterestTypeResponse
import com.tht.tht.data.remote.response.signup.SignupResponse
import com.tht.tht.domain.signup.model.SignupUserModel
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
internal class SignupRepositoryImplTest {

    private lateinit var repository: SignupRepositoryImpl
    private lateinit var apiDataSource: SignupApiDataSource
    private lateinit var signupUserDataSource: SignupUserDataSource
    private lateinit var termsDataSource: TermsDataSource
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setupTest() {
        apiDataSource = mockk(relaxed = true)
        signupUserDataSource = mockk(relaxed = true)
        termsDataSource = mockk(relaxed = true)
        repository = SignupRepositoryImpl(
            apiDataSource,
            signupUserDataSource,
            termsDataSource,
            testDispatcher
        )
    }

    @Test
    fun `saveSignupUser는 SignupUserDataSource의 saveSignupUser의 결과를 SignupUserModel로 변환해 리턴한다`() = runTest(testDispatcher) {
        val expect = mockk<SignupUserEntity>(relaxed = true)
        coEvery { signupUserDataSource.saveSignupUser(any(), any()) } returns expect
        val actual = repository.saveSignupUser(expect.toModel())
        assertThat(actual)
            .isEqualTo(expect.toModel())
    }

    @Test
    fun `fetchSignupUser는 SignupUserDataSource의 fetchSignupUser의 결과를 SignupUserModel로 변환해 리턴한다`() = runTest(testDispatcher) {
        val expect = mockk<SignupUserEntity>(relaxed = true)
        coEvery { signupUserDataSource.fetchSignupUser(any()) } returns expect
        val actual = repository.fetchSignupUser("phone")
        assertThat(actual)
            .isEqualTo(expect.toModel())
    }

    @Test
    fun `patchSignupUser는 매개변수 User를 Entity로 가공해 SignupUserDataSource의 saveSignupUser를 호출한다`() = runTest(testDispatcher) {
        val expect = SignupUserModel.getFromDefaultArgument()
        repository.patchSignupUser("phone", expect)
        coVerify { signupUserDataSource.saveSignupUser(any(), expect.toEntity()) }
    }

    @Test
    fun `removeSignupUser는 SignupUserDataSource의 removeSignupUser의 결과를 리턴한다`() = runTest(testDispatcher) {
        coEvery { signupUserDataSource.removeSignupUser(any()) } returns false
        val actual = repository.removeSignupUser("phone")
        assertThat(actual)
            .isFalse
    }

    @Test
    fun `requestAuthentication는 SignupApiDataSource의 requestAuthentication의 결과를 리턴한다`() = runTest(testDispatcher) {
        coEvery { apiDataSource.requestAuthenticationNumber(any()) } returns true
        val actual = repository.requestAuthentication("phone")
        assertThat(actual)
            .isTrue
    }

    @Test
    fun `requestVerify는 SignupApiDataSource의 requestVerify의 결과를 리턴한다`() = runTest(testDispatcher) {
        coEvery { apiDataSource.requestVerify(any(), any()) } returns true
        val actual = repository.requestVerify("phone", "auth")
        assertThat(actual)
            .isTrue
    }

    @Test
    fun `fetchTerms는 TermsDataSource의 fetchTerms의 결과를 Model로 가공해 리턴한다`() = runTest(testDispatcher) {
        val expect = TermsEntity(
            listOf(TermsEntity.Body(listOf(TermsEntity.Body.Content("content", "title")), true, "title"))
        )
        coEvery { termsDataSource.fetchSignupTerms() } returns expect
        val actual = repository.fetchTerms()
        assertThat(actual)
            .isEqualTo(expect.body.map { it.toModel() })
    }

    @Test
    fun `fetchInterest는 SignupApiDataSource의 fetchInterest의 결과를 Model로 가공해 리턴한다`() = runTest(testDispatcher) {
        val expect = listOf(InterestTypeResponse("name", "code", 0))
        coEvery { apiDataSource.fetchInterests() } returns expect
        val actual = repository.fetchInterest()
        assertThat(actual)
            .isEqualTo(expect.map { it.toModel() })
    }

    @Test
    fun `fetchIdealType는 SignupApiDataSource의 fetchIdealType의 결과를 Model로 가공해 리턴한다`() = runTest(testDispatcher) {
        val expect = listOf(IdealTypeResponse("name", "code", 0))
        coEvery { apiDataSource.fetchIdealTypes() } returns expect
        val actual = repository.fetchIdealType()
        assertThat(actual)
            .isEqualTo(expect.map { it.toModel() })
    }

    @Test
    fun `requestSignup는 SignupApiDataSource의 requestSignup 결과의 userId의 유효성을 리턴한다`() = runTest(testDispatcher) {
        val expect = SignupResponse("userId")
        coEvery { apiDataSource.requestSignup(any()) } returns expect
        val actual = repository.requestSignup(mockk(relaxed = true))
        assertThat(actual)
            .isEqualTo(expect.userId.isNotBlank())
    }

    @Test
    fun `saveSignupUser는 SignupUserDataSource의 saveSignupUser를 호출한다`() = runTest(testDispatcher) {
        repository.saveSignupUser(mockk(relaxed = true))
        coVerify { signupUserDataSource.saveSignupUser(any(), any()) }
    }

    @Test
    fun `fetchSignupUser는 SignupUserDataSource의 fetchSignupUser를 호출한다`() = runTest(testDispatcher) {
        repository.fetchSignupUser("phone")
        coVerify { signupUserDataSource.fetchSignupUser(any()) }
    }

    @Test
    fun `patchSignupUser는 SignupUserDataSource의 saveSignupUser를 호출한다`() = runTest(testDispatcher) {
        repository.patchSignupUser("phone", mockk(relaxed = true))
        coVerify { signupUserDataSource.saveSignupUser(any(), any()) }
    }

    @Test
    fun `removeSignupUser는 SignupUserDataSource의 removeSignupUser를 호출한다`() = runTest(testDispatcher) {
        repository.removeSignupUser("phone")
        coVerify { signupUserDataSource.removeSignupUser(any()) }
    }

    @Test
    fun `requestAuthentication는 SignupApiDataSource의 requestAuthenticationNumber를 호출한다`() = runTest(testDispatcher) {
        repository.requestAuthentication("phone")
        coVerify { apiDataSource.requestAuthenticationNumber(any()) }
    }

    @Test
    fun `requestVerify는 SignupApiDataSource의 requestVerify를 호출한다`() = runTest(testDispatcher) {
        repository.requestVerify("phone", "auth")
        coVerify { apiDataSource.requestVerify(any(), any()) }
    }

    @Test
    fun `fetchTerms는 TermsDataSource의 fetchTerms를 호출한다`() = runTest(testDispatcher) {
        repository.fetchTerms()
        coVerify { termsDataSource.fetchSignupTerms() }
    }

    @Test
    fun `fetchInterest는 SignupApiDataSource의 fetchInterest를 호출한다`() = runTest(testDispatcher) {
        repository.fetchInterest()
        coVerify { apiDataSource.fetchInterests() }
    }

    @Test
    fun `fetchIdealType는 SignupApiDataSource의 fetchIdealType를 호출한다`() = runTest(testDispatcher) {
        repository.fetchIdealType()
        coVerify { apiDataSource.fetchIdealTypes() }
    }

    @Test
    fun `requestSignup는 SignupApiDataSource의 requestSignup를 호출한다`() = runTest(testDispatcher) {
        repository.requestSignup(mockk(relaxed = true))
        coVerify { apiDataSource.requestSignup(any()) }
    }

}