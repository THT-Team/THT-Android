package com.tht.tht.data.remote.service

import com.tht.tht.data.constant.THTApiConstant
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.ideal.IdealTypeResponse
import com.tht.tht.data.remote.response.interests.InterestTypeResponse
import retrofit2.http.GET

interface ThtApi {

    @GET(THTApiConstant.Signup.IDEAL_TYPE)
    suspend fun fetchIdealType(): ThtResponse<List<IdealTypeResponse>>

    @GET(THTApiConstant.Signup.INTERESTS)
    suspend fun fetchInterestsType(): ThtResponse<List<InterestTypeResponse>>
}
