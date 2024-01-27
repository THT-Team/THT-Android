package com.tht.tht.data.datasource

import com.tht.tht.data.local.service.TermsService
import com.tht.tht.data.local.datasource.TermsDataSourceImpl
import com.tht.tht.data.local.entity.TermsEntity
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
internal class TermsDataSourceImplTest {

    private lateinit var dataSource: TermsDataSourceImpl
    private lateinit var dao: TermsService
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setUpTest() {
        dao = mockk(relaxed = true)
        dataSource = TermsDataSourceImpl(
            dao,
            testDispatcher
        )
    }

    @Test
    fun `fetchSignupTerms는 TermsDao의 fetchSignupTerms의 결과를 리턴한다`() = runTest(testDispatcher) {
        val expect = TermsEntity(
            listOf(
                TermsEntity.Body(
                    listOf(TermsEntity.Body.Content("content", "title")),
                    true,
                    "title",
                    "key",
                    "description1"
                )
            )
        )
        coEvery { dao.fetchTerms() } returns expect
        val actual = dataSource.fetchSignupTerms()

        assertThat(actual)
            .isEqualTo(expect)
    }

    @Test
    fun `fetchSignupTerms는 TermsDao의 fetchSignupTerms를 호출한다`() = runTest(testDispatcher) {
        dataSource.fetchSignupTerms()
        coVerify { dao.fetchTerms() }
    }
}
