package com.tht.tht.domain.image

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class UploadImageUseCase(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(uriList: List<String>, ): Result<List<String>> {
        return kotlin.runCatching {
            coroutineScope {
                uriList.mapIndexed { idx, uri ->
                    async {
                        kotlin.runCatching {
                            imageRepository.uploadImage(uri, "${System.currentTimeMillis()}_$idx")
                        }
                    }
                }.awaitAll()
                    .filter { it.isSuccess }
                    .map { it.getOrThrow() }
            }
        }
    }
}
