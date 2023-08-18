package com.tht.tht.domain.user

class SendDislikeUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userUuid: String): Result<Unit> {
        return kotlin.runCatching {
            repository.sendDislike(userUuid)
        }
    }
}
