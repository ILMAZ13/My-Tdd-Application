package com.flatstack.mytddapplication.api

import com.flatstack.mytddapplication.entities.RespBase
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.SerializedName
import retrofit2.Response

@Suppress("unused")
sealed class ApiResponse<T : RespBase> {
    companion object {
        fun <T : RespBase> create(error: Throwable): ApiErrorResponse<T> =
            ApiErrorResponse(error.message ?: "unknown error")

        fun <T : RespBase> create(response: Response<T>): ApiResponse<T> =
            if (response.isSuccessful) {
                response.body()?.let {
                    val apiResponse: ApiResponse<T> =
                        if (it.response) {
                            ApiSuccessResponse(it)
                        } else {
                            ApiErrorResponse(it.error ?: "")
                        }
                    apiResponse
                } ?: ApiEmptyResponse()
            } else {
                val msg = response.errorBody()?.string()
                val errorMessage: String =
                    if (msg.isNullOrEmpty()) {
                        response.message()
                    } else {
                        tryToParseErrorMessage(msg)
                    }
                ApiErrorResponse(errorMessage)
            }
    }
}

private fun tryToParseErrorMessage(msg: String): String =
    try {
        Gson().fromJson(msg, ErrorBody::class.java)?.error ?: msg
    } catch (e: JsonSyntaxException) {
        msg
    }

data class ApiErrorResponse<T : RespBase>(val errorMessage: String) : ApiResponse<T>()

data class ApiSuccessResponse<T : RespBase>(val body: T) : ApiResponse<T>()

class ApiEmptyResponse<T : RespBase> : ApiResponse<T>()

private data class ErrorBody(
    @SerializedName(value = "error", alternate = ["Error", "ERROR"])
    val error: String
)
