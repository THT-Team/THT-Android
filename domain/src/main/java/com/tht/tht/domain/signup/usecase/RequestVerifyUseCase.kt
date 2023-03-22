package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RequestVerifyUseCase(
    private val repository: SignupRepository,
    private val createSignupUserUseCase: CreateSignupUserUseCase,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(apiAuthNum: String, phone: String, inputAuthNum: String): Result<Boolean> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                (apiAuthNum == inputAuthNum).also {
                    if(it && repository.fetchSignupUser(phone) == null) { // 해당 phone 으로 진행 중인 가입 프로세스가 없다면, 새로 생성
                        createSignupUserUseCase(phone)
                    }
                }
            }
        }
    }
}
