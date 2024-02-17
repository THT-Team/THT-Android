package com.tht.tht.domain.setting.usecase

import com.tht.tht.domain.setting.model.MyPageUserInfoModel
import com.tht.tht.domain.setting.repository.UserSettingRepository

class FetchMyPageUserInfoUseCase(
    private val repository: UserSettingRepository
) {
    suspend operator fun invoke(): Result<MyPageUserInfoModel> {
        return kotlin.runCatching {
            repository.fetchMyPageUserInfo()
        }
    }
}
