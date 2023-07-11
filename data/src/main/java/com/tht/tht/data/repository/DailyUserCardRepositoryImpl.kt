package com.tht.tht.data.repository

import com.tht.tht.data.remote.datasource.dailyusercard.DailyUserCardDataSource
import com.tht.tht.data.remote.mapper.toModel
import com.tht.tht.data.remote.request.dailyusercard.DailyUserCardRequest
import com.tht.tht.domain.dailyusercard.DailyUserCardListModel
import com.tht.tht.domain.dailyusercard.DailyUserCardRepository
import javax.inject.Inject

class DailyUserCardRepositoryImpl @Inject constructor(
    private val dataSource: DailyUserCardDataSource
) : DailyUserCardRepository {
    override suspend fun fetchDailyUserCard(
        passedUserIdList: List<String>,
        lastUserDailyFallingCourserIdx: Int,
        size: Int
    ): DailyUserCardListModel {
        return dataSource.fetchDailyUserCard(
            DailyUserCardRequest(
                alreadySeenUserUuidList = passedUserIdList,
                size = size,
                userDailyFallingCourserIdx = lastUserDailyFallingCourserIdx
            )
        ).toModel()
    }
}
