package com.tht.tht.domain.user

class SendHeartUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        userUuid: String,
        selectDailyTopicIdx: Int
    ): Result<Boolean> {
        return kotlin.runCatching {
            repository.sendHeart(userUuid, selectDailyTopicIdx)
        }
    }
}
