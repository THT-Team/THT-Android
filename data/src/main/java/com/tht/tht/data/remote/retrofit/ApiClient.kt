package com.tht.tht.data.remote.retrofit

import com.google.gson.Gson
import com.tht.tht.data.constant.THTApiConstant
import com.tht.tht.data.remote.retrofit.callAdapter.ApiCallAdapterFactory
import com.tht.tht.data.remote.service.THTLoginApi
import com.tht.tht.data.remote.service.RegionCodeApi
import com.tht.tht.data.remote.service.THTSignupApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiClient @Inject constructor(
    private val apiGson: Gson,
    private val okHttpClient: OkHttpClient
) {

    private val apiAdapter: Retrofit by lazy {
        createApiAdapter(THTApiConstant.BASE_URL)
    }

    private fun createApiAdapter(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(apiGson))
            .addCallAdapterFactory(ApiCallAdapterFactory())
            .build()
    }

    fun provideTHTSignupApi(): THTSignupApi = apiAdapter.create(THTSignupApi::class.java)

    fun provideRegionCodeApi(): RegionCodeApi = apiAdapter.create(RegionCodeApi::class.java)

    fun provideRetrofit(): Retrofit = apiAdapter

    fun provideTHTLoginApi(): THTLoginApi = apiAdapter.create(THTLoginApi::class.java)
}
