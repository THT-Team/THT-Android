package com.tht.tht.data.repository

import android.net.Uri
import com.tht.tht.data.remote.datasource.signup.ImageDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

/**
 * Uri 를 사용 하기 때문에 Android Test
 */
@ExperimentalCoroutinesApi
internal class ImageRepositoryImplTest {

    private lateinit var repository: ImageRepositoryImpl
    private lateinit var dataSource: ImageDataSource

    private var uriString = "myapp://home/payments"

    @Before
    fun setupTest() {
        dataSource = mockk(relaxed = true)
        repository = ImageRepositoryImpl(
            dataSource
        )
    }

    @Test
    fun requestSignup_return_pair_result_of_uploadImage_and_idx() = runTest {
        val expectUrl = "url"
        val expectIdx = 2
        coEvery { dataSource.uploadImage(Uri.parse(""), "") } returns expectUrl
        val actual = repository.uploadImageWithIndex("", "", expectIdx)

        Assertions.assertThat(actual)
            .isEqualTo(expectUrl to expectIdx)
    }

    @Test
    fun requestSignup_called_uploadImage_with_passed_params() = runTest {
        val requestUrl = "requestUrl"
        val saveFileName = "saveFileName"
        repository.uploadImageWithIndex(requestUrl, saveFileName, 0)
        coVerify { dataSource.uploadImage(Uri.parse(requestUrl), saveFileName) }
    }

    @Test
    fun requestSignup_called_uploadImage() = runTest {
        repository.uploadImageWithIndex(uriString, "", 0)
        coVerify { dataSource.uploadImage(any(), any()) }
    }
}
