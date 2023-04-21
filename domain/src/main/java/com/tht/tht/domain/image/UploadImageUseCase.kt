package com.tht.tht.domain.image

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class UploadImageUseCase(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(uriList: List<Pair<String, Int>>): Result<List<Pair<String, Int>>> {
        return kotlin.runCatching {
            coroutineScope {
                uriList.map { uriInfo ->
                    async {
                        kotlin.runCatching {
                            imageRepository.uploadImageWithIndex(
                                uriInfo.first,
                                "${System.currentTimeMillis()}_${uriInfo.second}",
                                uriInfo.second
                            )
                        }
                    }
                }.awaitAll()
                    .map { it.getOrThrow() }
            }
        }
    }
}
