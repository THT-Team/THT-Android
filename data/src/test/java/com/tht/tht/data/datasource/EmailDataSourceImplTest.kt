package com.tht.tht.data.datasource

import com.tht.tht.data.remote.datasource.EmailDataSource
import com.tht.tht.data.remote.datasource.EmailDataSourceImpl
import com.tht.tht.data.remote.service.email.EmailService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
class EmailDataSourceImplTest {

    private lateinit var emailService: EmailService
    private lateinit var emailDataSource: EmailDataSource
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setUpTest() {
        emailService = mockk(relaxed = true)
        emailDataSource = EmailDataSourceImpl(
            emailService,
            testDispatcher
        )
    }

    @Test
    fun `sendEmail은 EmailService의 sendEmail을 호출한다`() = runTest(testDispatcher) {
        emailDataSource.sendEmail("")
        coVerify(exactly = 1) { emailService.sendEmail("") }
    }

    @Test
    fun `EmailService의 sendEmail에서 예외가 발생하면 datasource에서도 예외가 발생한다`() {
        coEvery { emailService.sendEmail("exception") } throws Exception("exception")
        assertThrows(Exception::class.java) {
            runTest(testDispatcher) { emailDataSource.sendEmail("exception") }
        }
    }
}
