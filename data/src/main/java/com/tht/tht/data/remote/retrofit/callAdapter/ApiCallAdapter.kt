package com.tht.tht.data.remote.retrofit.callAdapter

import com.tht.tht.data.remote.response.BaseResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

class ApiCallAdapter<T : Any, E : Any>(
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody, E>
) : CallAdapter<T, Call<BaseResponse<T, E>>> {

    override fun responseType() = successType

    override fun adapt(call: Call<T>): Call<BaseResponse<T, E>> =
        ApiCall(call, errorBodyConverter)
}
