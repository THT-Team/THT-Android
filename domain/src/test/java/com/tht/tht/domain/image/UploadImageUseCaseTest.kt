package com.tht.tht.domain.image

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
internal class UploadImageUseCaseTest {

    private lateinit var useCase: UploadImageUseCase
    private lateinit var repository: ImageRepository

    @Before
    fun setupTest() {
        repository = mockk(relaxed = true)
        useCase = UploadImageUseCase(
            repository
        )
    }

    @Test
    fun `useCase는 Repository의 uploadImageWithIndex결과와 매개변수 idx Pair를 Result로 래핑해 리턴한다`() = runTest {
        val expect = listOf(Pair("url", 0))
        coEvery { repository.uploadImageWithIndex(any(), any(), any()) } returns expect.first()
        val actual = useCase(expect)

        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.getOrNull())
            .isNotNull
            .isEqualTo(expect)
    }

    @Test
    fun `useCase는 Repository가 Exception을 발생시키면 Result로 래핑해 리턴한다`() = runTest {
        val unitTextException = java.lang.Exception("unitTest")
        coEvery { repository.uploadImageWithIndex(any(), any(), any()) } throws unitTextException
        val actual = useCase(listOf("url" to 2))
        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull()?.message)
            .isNotNull
            .isEqualTo(unitTextException.message)
    }

    @Test
    fun `useCase는 매개변수 uriList의 Item의 정보를 Repository의 uploadImageWithIndex의 매개변수로 전달한다`() = runTest {
        val expect = "url" to 1
        useCase(listOf(expect))
        coVerify { repository.uploadImageWithIndex(expect.first, any(), expect.second) }
    }

    @Test
    fun `useCase는 매개변수 uriList의 size만큼 Repository의 uploadImageWithIndex를 호출한다`() = runTest {
        val uriList = listOf(
            "url" to 0,
            "url1" to 1,
            "url2" to 2
        )
        useCase(uriList)
        coVerify(exactly = uriList.size) { repository.uploadImageWithIndex(any(), any(), any()) }
    }

    @Test
    fun `useCase는 Repository의 uploadImageWithIndex를 호출한다`() = runTest {
        useCase(listOf("url" to 2))
        coVerify { repository.uploadImageWithIndex(any(), any(), any()) }
    }
}
