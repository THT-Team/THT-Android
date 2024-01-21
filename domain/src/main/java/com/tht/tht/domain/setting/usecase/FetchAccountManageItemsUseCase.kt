package com.tht.tht.domain.setting.usecase

import com.tht.tht.domain.setting.model.SettingSectionModel
import com.tht.tht.domain.setting.repository.SettingRepository

class FetchAccountManageItemsUseCase(
    private val repository: SettingRepository
) {
    suspend operator fun invoke(): Result<List<SettingSectionModel>> {
        return kotlin.runCatching {
            repository.fetchSettingAccountManagerItemList()
        }
    }
}
