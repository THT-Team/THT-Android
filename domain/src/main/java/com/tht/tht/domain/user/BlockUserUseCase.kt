package com.tht.tht.domain.user

class BlockUserUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(userUuid: String): Result<Unit> {
        return kotlin.runCatching {
            repository.blockUser(userUuid)
        }
    }
}
