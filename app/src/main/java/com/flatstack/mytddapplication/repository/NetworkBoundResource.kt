package com.flatstack.mytddapplication.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.flatstack.mytddapplication.AppExecutors
import com.flatstack.mytddapplication.api.ApiEmptyResponse
import com.flatstack.mytddapplication.api.ApiErrorResponse
import com.flatstack.mytddapplication.api.ApiResponse
import com.flatstack.mytddapplication.api.ApiSuccessResponse
import com.flatstack.mytddapplication.entities.Resource
import com.flatstack.mytddapplication.entities.RespBase

abstract class NetworkBoundResource<ResultType, RequestType : RespBase>(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        val dbSource = this.loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) {
                    setValue(Resource.success(it))
                }
            }
        }
    }

    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        result.addSource(dbSource) {
            setValue(Resource.loading(it))
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.diskIO.execute {
                        saveCallResult(processResponse(response))
                        appExecutors.mainThread.execute {
                            result.addSource(loadFromDb()) {
                                setValue(Resource.success(it))
                            }
                        }
                    }
                }
                is ApiEmptyResponse -> {
                    result.addSource(loadFromDb()) {
                        setValue(Resource.success(it))
                    }
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    result.addSource(dbSource) {
                        setValue(Resource.error(response.errorMessage, it))
                    }
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}
