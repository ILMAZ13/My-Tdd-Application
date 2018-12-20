package com.flatstack.mytddapplication.entities

import com.flatstack.mytddapplication.entities.Status.ERROR
import com.flatstack.mytddapplication.entities.Status.LOADING
import com.flatstack.mytddapplication.entities.Status.SUCCESS

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}
