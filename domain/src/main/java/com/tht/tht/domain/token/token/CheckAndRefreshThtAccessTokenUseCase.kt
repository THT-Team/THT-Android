package com.tht.tht.domain.token.token

import com.tht.tht.domain.extension.resultRetry
import com.tht.tht.domain.login.usecase.RefreshFcmTokenLoginUseCase

/**
 * Local 에 저장된 Access Token 이 유효 하지 않다면 RETRY_COUNT 회 Refresh 시도
 */
class CheckAndRefreshThtAccessTokenUseCase(
    private val refreshFcmTokenLoginUseCase: RefreshFcmTokenLoginUseCase,
    private val checkThtAccessTokenExpiredUseCase: CheckThtAccessTokenExpiredUseCase
) {
    suspend operator fun invoke(
        refreshRetryCount: Int = RETRY_COUNT
    ): Result<Boolean> {
        return kotlin.runCatching {
            when (checkThtAccessTokenExpiredUseCase().getOrNull() ?: false) {
                true -> true
                else -> {
                    resultRetry(refreshRetryCount) { refreshFcmTokenLoginUseCase() }
                        .let { !it.getOrNull()?.accessToken.isNullOrBlank() }
                }
            }
        }
    }

    companion object {
        private const val RETRY_COUNT = 1
    }
}
