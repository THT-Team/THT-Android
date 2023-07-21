package com.tht.tht.data.datasource

import com.tht.tht.data.remote.datasource.signup.RegionCodeDataSourceImpl
import com.tht.tht.data.remote.mapper.toUnwrap
import com.tht.tht.data.remote.response.base.BaseResponse
import com.tht.tht.data.remote.response.base.ErrorResponse
import com.tht.tht.data.remote.response.regioncode.RegionCodeResponse
import com.tht.tht.data.remote.service.location.RegionCodeApi
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
internal class RegionCodeDataSourceImplTest {

    private lateinit var dataSource: RegionCodeDataSourceImpl
    private lateinit var regionCodeApi: RegionCodeApi
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setUpTest() {
        regionCodeApi = mockk(relaxed = true)
        dataSource = RegionCodeDataSourceImpl(
            regionCodeApi,
            testDispatcher
        )
    }

    @Test
    fun `fetchRegionCode는 RegionCodeApi의 fetchRegionCode의 결과를 unwrapping한 결과를 리턴한다`() = runTest(testDispatcher) {
        val expect = BaseResponse.Success(
            statusCode = 200,
            response = RegionCodeResponse(
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
        )
        coEvery { regionCodeApi.fetchRegionCode("경기도 성남시 중원구") } returns expect
        val actual = dataSource.fetchRegionCode("경기도 성남시 중원구")
        assertThat(actual)
            .isEqualTo(expect.toUnwrap { it })
    }

    @Test
    fun `fetchRegionCode는 RegionCodeApi의 fetchRegionCode가 실패할시 예외를 발생시킨다 (Api Error)`() = runTest(testDispatcher) {
        val expect = BaseResponse.ApiError(
            statusCode = 500,
            errorResponse = ErrorResponse(
                0,"","ApiError","",""
            )
        )
        coEvery { regionCodeApi.fetchRegionCode("") } returns expect

        val result = runCatching {
            dataSource.fetchRegionCode("")
        }

        assertThat(result.exceptionOrNull())
            .isInstanceOf(Exception::class.java)
            .hasMessage("ApiError")
    }

    @Test
    fun `fetchRegionCode는 RegionCodeApi의 fetchRegionCode가 실패할시 예외를 발생시킨다 (Network Error)`() = runTest(testDispatcher) {
        val expect = BaseResponse.ApiError(
            statusCode = 400,
            errorResponse = ErrorResponse(
                0,"","NetworkError","",""
            )
        )
        coEvery { regionCodeApi.fetchRegionCode("") } returns expect

        val result = runCatching {
            dataSource.fetchRegionCode("")
        }

        assertThat(result.exceptionOrNull())
            .isInstanceOf(Exception::class.java)
            .hasMessage("NetworkError")
    }

    @Test
    fun `fetchRegionCode는 RegionCodeApi의 fetchRegionCode가 실패할시 예외를 발생시킨다 (Unknown Error)`() = runTest(testDispatcher) {
        val expect = BaseResponse.ApiError(
            statusCode = 0,
            errorResponse = ErrorResponse(
                0,"","UnknownError","",""
            )
        )
        coEvery { regionCodeApi.fetchRegionCode("") } returns expect

        val result = runCatching {
            dataSource.fetchRegionCode("")
        }

        assertThat(result.exceptionOrNull())
            .isInstanceOf(Exception::class.java)
            .hasMessage("UnknownError")
    }
}
