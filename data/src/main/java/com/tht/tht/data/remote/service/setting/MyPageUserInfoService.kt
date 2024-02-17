package com.tht.tht.data.remote.service.setting

import com.tht.tht.data.constant.THTApiConstant
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.setting.MyPageUserInfoResponse
import retrofit2.http.GET

interface MyPageUserInfoService {
    @GET(THTApiConstant.Setting.MY_PAGE)
    suspend fun fetchMyPageUserInfo(): ThtResponse<MyPageUserInfoResponse>
}
