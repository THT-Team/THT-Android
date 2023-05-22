package com.tht.tht.data.remote.service

import com.tht.tht.data.BuildConfig
import com.tht.tht.data.constant.RegionCodeConstant
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.regioncode.RegionCodeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RegionCodeApi {

    @GET(RegionCodeConstant.REGION_LIST)
    suspend fun fetchRegionCode(
        @Query("locatadd_nm") address: String,
        @Query("ServiceKey") serviceKey: String = BuildConfig.REGION_CODE_SERVICE_KEY,
        @Query("type") type: String = RegionCodeConstant.TYPE,
        @Query("pageNo") pageNo: Int = RegionCodeConstant.PAGE_NO,
        @Query("numOfRows") numOfRows: Int = RegionCodeConstant.NUM_OF_ROWS,
        @Query("flag") flag: String = RegionCodeConstant.FLAG
    ): ThtResponse<RegionCodeResponse>
}
