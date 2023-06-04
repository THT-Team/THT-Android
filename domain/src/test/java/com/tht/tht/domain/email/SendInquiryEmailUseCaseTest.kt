package com.tht.tht.domain.email

import com.tht.tht.domain.email.constant.EmailServiceConstant
import com.tht.tht.domain.email.repository.EmailRepository
import com.tht.tht.domain.email.usecase.SendInquiryEmailUseCase
import io.mockk.coEvery
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
internal class SendInquiryEmailUseCaseTest {

    private lateinit var useCase: SendInquiryEmailUseCase
    private lateinit var repository: EmailRepository
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setupTest() {
        repository = mockk(relaxed = true)
        useCase = SendInquiryEmailUseCase(
            repository
        )
    }

    @Test
    fun `useCase는 Repository의 sendEmail의 결과를 Result로 래핑해서 리턴한다`() = runTest(testDispatcher) {
        coEvery { repository.sendEmail("", "", "") } returns mockk()
        val actual = useCase("", "")

        Assertions.assertThat(actual)
            .isInstanceOf(Result::class.java)

        Assertions.assertThat(actual.getOrNull())
            .isNotNull
    }

    @Test
    fun `useCase는 Repository에서 에러를 전달하면 Result로 래핑하여 전달한다`() = runTest(testDispatcher) {
        val expect = Exception("unit test exception")
        coEvery {
            repository.sendEmail(
                EmailServiceConstant.TITLE,
                "${EmailServiceConstant.CONTENT}\n\n\n",
                EmailServiceConstant.RECIPIENT
            )
        } throws expect

        val actual = useCase("", "")

        Assertions.assertThat(actual)
            .isInstanceOf(Result::class.java)

        Assertions.assertThat(actual.exceptionOrNull()?.message)
            .isNotNull
            .isEqualTo(expect.message)
    }
}
