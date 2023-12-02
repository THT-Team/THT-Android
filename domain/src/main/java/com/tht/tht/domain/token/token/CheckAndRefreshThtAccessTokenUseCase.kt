package com.tht.tht.domain.token.token

import com.tht.tht.domain.extension.resultRetry
import com.tht.tht.domain.login.usecase.RefreshFcmTokenLoginUseCase

/**
 * Local 에 저장된 Access Token 이 유효 하지 않다면 RETRY_COUNT 회 Refresh 시도
 *  - Refresh 실패 시, 새로 로그인하면 로그인 정보가 덮어씌워지기에, 로그인 초기화까지는 시키지 않음
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
        private const val RETRY_COUNT = 0
    }
}
