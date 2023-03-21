package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay

/**
 *  중복 검사 API 구현 이전, 단순한 테스트용
 *  랜덤으로 API 성공[중복x, 중복o], API 실패 3가지 CASE를 리턴
 */
class CheckNicknameDuplicateUseCase(
    private val repository: SignupRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(nickname: String): Result<Boolean> {
        delay(100)
        val rValue = (1 until 4).random()
        println("Random value => $rValue")
        return when (rValue % 3) {
            0 -> Result.success(true)
            1 -> Result.success(false)
            else -> Result.failure(Throwable("test"))
        }
    }
}
