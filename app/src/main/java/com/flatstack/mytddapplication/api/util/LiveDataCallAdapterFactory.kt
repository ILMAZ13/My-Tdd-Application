package com.flatstack.mytddapplication.api.util

import androidx.lifecycle.LiveData
import com.flatstack.mytddapplication.api.ApiResponse
import com.flatstack.mytddapplication.entities.RespBase
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        if (rawObservableType != ApiResponse::class.java) {
            throw IllegalArgumentException("Type must be ApiResponse<T>")
        }
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("ApiResponse must be parameterized")
        }
        val bodyType = getParameterUpperBound(0, observableType)
        return LiveDataCallAdapter<RespBase>(bodyType)
    }
}
