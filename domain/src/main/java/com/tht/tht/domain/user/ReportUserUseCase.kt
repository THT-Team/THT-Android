package com.tht.tht.domain.user

class ReportUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        userUuid: String,
        reason: String
    ): Result<Unit> {
        return kotlin.runCatching {
            repository.reportUser(userUuid, reason)
        }
    }
}
