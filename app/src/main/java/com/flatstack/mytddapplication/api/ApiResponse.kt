package com.flatstack.mytddapplication.api

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.SerializedName
import retrofit2.Response

@Suppress("unused")
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> =
            ApiErrorResponse(error.message ?: "unknown error")

        fun <T> create(response: Response<T>): ApiResponse<T> =
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiSuccessResponse(it)
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

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

class ApiEmptyResponse<T> : ApiResponse<T>()

private data class ErrorBody(
    @SerializedName(value = "error", alternate = ["Error", "ERROR"])
    val error: String
)
