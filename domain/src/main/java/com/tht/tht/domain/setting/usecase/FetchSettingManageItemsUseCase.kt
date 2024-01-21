package com.tht.tht.domain.setting.usecase

import com.tht.tht.domain.setting.model.SettingSectionModel
import com.tht.tht.domain.setting.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FetchSettingManageItemsUseCase(
    private val repository: SettingRepository
) {
    suspend operator fun invoke(): Flow<Result<List<SettingSectionModel>>> = flow {
        val items = repository.fetchSettingMangerItemList()
        emit(items)
        // location 등 빈 값들 fetch 후 emit
    }.map {
        Result.success(it)
    }.catch {
        emit(Result.failure(it))
    }
}
