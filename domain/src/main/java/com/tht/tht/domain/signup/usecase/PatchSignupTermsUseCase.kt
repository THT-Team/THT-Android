package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.SignupException
import com.tht.tht.domain.signup.model.TermsModel
import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PatchSignupTermsUseCase(
    private val repository: SignupRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(phone: String, termsAgreement: Map<TermsModel, Boolean>): Result<Boolean> {
        return kotlin.runCatching {
            for(term in termsAgreement) {
                if(term.key.require && !term.value){
                    throw SignupException.TermsRequireInvalidateException(term.key.title)
                }
            }
            withContext(dispatcher) {
                repository.patchSignupUser(
                    phone,
                    requireNotNull(repository.fetchSignupUser(phone)){
                        throw SignupException.SignupUserInvalidateException()
                    }.copy(termsAgreement = termsAgreement))
            }
        }
    }
}
