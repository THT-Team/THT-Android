package com.tht.tht.data.repository

import com.tht.tht.data.local.mapper.toModel
import com.tht.tht.data.remote.datasource.signup.RegionCodeDataSource
import com.tht.tht.data.remote.response.regioncode.RegionCodeResponse
import com.tht.tht.domain.signup.repository.RegionCodeRepository
import io.mockk.coEvery
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
internal class RegionCodeRepositoryImplTest {

    private lateinit var repository: RegionCodeRepository
    private lateinit var apiDataSource: RegionCodeDataSource
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setupTest() {
        apiDataSource = mockk(relaxed = true)
        repository = RegionCodeRepositoryImpl(
            apiDataSource,
            testDispatcher
        )
    }

    @Test
    fun `fetchRegionCode는 RegionCodeDataSource의 fetchRegionCode의 결과를 RegionModel로 변환해 리턴한다`() =
        runTest(testDispatcher) {
            val expect = RegionCodeResponse(
                listOf(
                    RegionCodeResponse.StanReginCd(
                        null
                    ),
                    RegionCodeResponse.StanReginCd(
                        listOf(
                            RegionCodeResponse.StanReginCd.Row("4113300000")
                        )
                    )
                )
            )
            coEvery { apiDataSource.fetchRegionCode("경기도 성남시 중원구") } returns expect
            val actual = repository.fetchRegionCode("경기도 성남시 중원구")
            assertThat(actual)
                .isEqualTo(expect.toModel())
        }
}
