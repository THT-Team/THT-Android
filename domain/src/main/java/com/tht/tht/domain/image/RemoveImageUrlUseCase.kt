package com.tht.tht.domain.image

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class RemoveImageUrlUseCase(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(urlList: List<String>): Result<List<Boolean>> {
        return kotlin.runCatching {
            coroutineScope {
                urlList.map { url ->
                    val fileName = url.split('/').let {
                        if (it.size == 1) {
                            null
                        } else {
                            it.last().split('?').firstOrNull()
                        }
                    }
                    async {
                        kotlin.runCatching {
                            when (fileName.isNullOrBlank()) {
                                true -> false
                                else -> imageRepository.removeImage(fileName)
                            }
                        }
                    }
                }.awaitAll()
                    .map { it.getOrThrow() }
            }
        }
    }
}
