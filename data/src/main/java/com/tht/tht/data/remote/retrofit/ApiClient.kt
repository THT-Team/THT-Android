package com.tht.tht.data.remote.retrofit

import com.google.gson.Gson
import com.tht.tht.data.remote.retrofit.callAdapter.ApiCallAdapterFactory
import com.tht.tht.data.remote.service.ThtApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiClient @Inject constructor(
    private val apiGson: Gson
) {

    @Inject
    lateinit var okHttpClient: OkHttpClient

    private val apiAdapter: Retrofit by lazy {
        createApiAdapter("")
    }

    private fun createApiAdapter(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(apiGson))
            .addCallAdapterFactory(ApiCallAdapterFactory())
            .build()
    }

    fun provideThtApi(): ThtApi = apiAdapter.create(ThtApi::class.java)
}
