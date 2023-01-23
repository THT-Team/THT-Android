package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.constant.SignupConstant
import com.tht.tht.domain.signup.model.SignupException
import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PatchSignupProfileImagesUseCase(
    private val repository: SignupRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(phone: String, profileImgUrl: List<String>): Result<Boolean> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                if(profileImgUrl.size <SignupConstant.ProfileImageRequireSize){
                    throw SignupException.InputDataRequireSizeException("profileImage", SignupConstant.ProfileImageRequireSize)
                }
                repository.patchSignupUser(
                    phone,
                    requireNotNull(repository.fetchSignupUser(phone)){
                        throw SignupException.SignupUserInvalidateException()
                    }.copy(profileImgUrl = profileImgUrl)
                )
            }
        }
    }
}
