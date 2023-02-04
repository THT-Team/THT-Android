package com.tht.tht.data.remote.service

import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.ideal.IdealTypeResponse
import retrofit2.http.GET

interface ThtApi {

    @GET("/ideal-types")
    suspend fun fetchIdealType(): ThtResponse<List<IdealTypeResponse>>
}
