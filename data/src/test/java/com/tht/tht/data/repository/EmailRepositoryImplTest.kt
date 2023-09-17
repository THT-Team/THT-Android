package com.tht.tht.data.repository

import com.tht.tht.data.remote.datasource.signup.EmailDataSource
import com.tht.tht.domain.email.repository.EmailRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
internal class EmailRepositoryImplTest {

    private lateinit var repository: EmailRepository
    private lateinit var datasource: EmailDataSource
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setupTest() {
        datasource = mockk(relaxed = true)
        repository = EmailRepositoryImpl(
            datasource
        )
    }

    @Test
    fun `sendEmail은 EmailDataSource의 sendEmail을 호출한다`() = runTest(testDispatcher) {
        repository.sendEmail("", "", "")
        coVerify(exactly = 1) { datasource.sendEmail("", "", "") }
    }

    @Test
    fun `EmailDataSource의 sendEmail에서 예외가 발생하면 repository에서도 예외가 발생한다`() {
        coEvery { datasource.sendEmail("", "", "") } throws Exception("exception")
        Assert.assertThrows(Exception::class.java) {
            runTest(testDispatcher) { repository.sendEmail("", "", "") }
        }
    }
}
