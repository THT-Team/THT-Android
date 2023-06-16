package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.SignupUserModel
import com.tht.tht.domain.signup.model.SignupUserModel.Companion.getFromDefaultArgument
import com.tht.tht.domain.signup.repository.SignupRepository
import com.tht.tht.domain.type.SignInType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CreateSignupUserUseCase(
    private val repository: SignupRepository,
    private val dispatcher: CoroutineDispatcher
) {
    internal suspend operator fun invoke(
        phone: String,
        signInType: SignInType
    ): SignupUserModel {
        return withContext(dispatcher) {
            repository.saveSignupUser(
                getFromDefaultArgument(
                    phone = phone,
                    snsType = signInType.key
                )
            )
        }
    }
}
