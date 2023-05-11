package com.tht.tht.domain.image

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
internal class RemoveImageUrlUseCaseTest {

    private lateinit var useCase: RemoveImageUrlUseCase
    private lateinit var repository: ImageRepository

    private val validUrl = "https://firebasestorage.googleapis.com/v0/b/tht-android-a954a.appspot.com/o/1683307361492_2?alt=media&token=fd9c27e5-1983-46cf-bc5b-d3f0ee65cbeb"
    private val invalidUrl = "1234"
    private val imageFileName = "1683307361492_2"

    @Before
    fun setupTest() {
        repository = mockk(relaxed = true)
        useCase = RemoveImageUrlUseCase(
            repository
        )
    }

    @Test
    fun `useCase는 Repository의 결과의 List를 Result로 래핑해 리턴한다`() = runTest {
        coEvery { repository.removeImage(any()) } returns true
        val actual = useCase(listOf(validUrl))
        val expect = listOf(true)
        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.getOrNull())
            .isNotNull
            .isEqualTo(expect)
    }


    @Test
    fun `useCase는 Repository가 Exception을 발생시키면 Result로 래핑해 리턴한다`() = runTest {
        val unitTextException = java.lang.Exception("unitTest")
        coEvery { repository.removeImage(any()) } throws unitTextException
        val actual = useCase(listOf(validUrl))
        assertThat(actual)
            .isInstanceOf(Result::class.java)

        assertThat(actual.exceptionOrNull()?.message)
            .isNotNull
            .isEqualTo(unitTextException.message)
    }


    @Test
    fun `useCase는 추출한 fileName을 Repository의 removeImage의 매개변수로 전달한다`() = runTest {
        useCase(listOf(validUrl))
        coVerify { repository.removeImage(imageFileName) }
    }

    @Test
    fun `useCase는 fileName 추출을 성공한 만큼 Repository의 removeImage를 호출한다`() = runTest {
        useCase(listOf(invalidUrl, validUrl, validUrl))
        coVerify(exactly = 2) { repository.removeImage(any()) }
    }

    @Test
    fun `useCase는 fileName 추출을 실패하면 Repository의 removeImage를 호출하지 않는다`() = runTest {
        useCase(listOf(invalidUrl, invalidUrl))
        coVerify(exactly = 0) { repository.removeImage(any()) }
    }

    @Test
    fun `useCase는 fileName 추출을 성공하면 Repository의 removeImage를 호출한다`() = runTest {
        useCase(listOf(validUrl, validUrl))
        coVerify(exactly = 2) { repository.removeImage(any()) }
    }
}
