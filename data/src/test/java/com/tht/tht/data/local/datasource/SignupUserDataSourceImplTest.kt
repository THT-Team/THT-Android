package com.tht.tht.data.local.datasource

import com.tht.tht.data.local.dao.SignupUserDao
import com.tht.tht.data.local.mapper.toEntity
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
internal class SignupUserDataSourceImplTest {
    private lateinit var dataSource: SignupUserDataSourceImpl
    private lateinit var dao: SignupUserDao
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setup() {
        dao = mockk(relaxed = true)
        dataSource = SignupUserDataSourceImpl(dao, testDispatcher)
    }

    @Test
    fun `saveSignupUser은 Dao의 saveSignupUser의 결과를 리턴한다`() = runTest(testDispatcher) {
        val expect = SignupUserModel.getFromDefaultArgument().copy(phone = "phone").toEntity()
        coEvery { dao.saveSignupUser(expect.phone, expect) } returns expect
        val actual = dataSource.saveSignupUser(expect.phone, expect)
        assertThat(actual)
            .isEqualTo(expect)
    }

    @Test
    fun `fetchSignupUser은 Dao의 fetchSignupUser의 결과를 리턴한다`() = runTest(testDispatcher) {
        val expect = SignupUserModel.getFromDefaultArgument().copy(phone = "phone").toEntity()
        coEvery { dao.fetchSignupUser(expect.phone) } returns expect
        val actual = dataSource.fetchSignupUser(expect.phone)
        assertThat(actual)
            .isEqualTo(expect)
    }

    @Test
    fun `removeSignupUser은 Dao의 removeSignupUser의 결과를 리턴한다`() = runTest(testDispatcher) {
        coEvery { dao.removeSignupUser(any()) } returns true
        val actual = dataSource.removeSignupUser("phone")
        assertThat(actual)
            .isTrue
    }

    @Test
    fun `saveSignupUser은 Dao의 saveSignupUser를 호출한다`() = runTest(testDispatcher) {
        val expect = SignupUserModel.getFromDefaultArgument().copy(phone = "phone").toEntity()
        dataSource.saveSignupUser(expect.phone, expect)
        coVerify { dao.saveSignupUser(expect.phone, expect) }
    }

    @Test
    fun `fetchSignupUser은 Dao의 fetchSignupUser를 호출한다`() = runTest(testDispatcher) {
        dataSource.fetchSignupUser("phone")
        coVerify { dao.fetchSignupUser("phone") }
    }

    @Test
    fun `removeSignupUser은 Dao의 removeSignupUser를 호출한다`() = runTest(testDispatcher) {
        dataSource.removeSignupUser("phone")
        coVerify { dao.removeSignupUser("phone") }
    }
}
