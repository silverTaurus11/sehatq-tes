package com.project.sehatqtest.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.project.sehatqtest.data.AppExecutors
import com.project.sehatqtest.data.source.remote.network.ApiResponse


abstract class NetworkBoundResourceLiveData <ResultType, RequestType>(private val mExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    @Suppress("LeakingThis")
    private val dbSource by lazy { loadFromDB() }

    init {
        result.value = Resource.Loading()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork()
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = Resource.Success(newData)
                }
            }
        }
    }

    protected open fun onFetchFailed(throwable: Throwable) {}

    protected abstract fun loadFromDB(): LiveData<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

    fun fetchFromNetwork(removeResource: Boolean = false) {

        val apiResponse = createCall()

        if(removeResource) result.removeSource(dbSource)
        result.addSource(dbSource) { newData ->
            result.value = Resource.Loading(newData)
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is ApiResponse.Success ->
                    mExecutors.diskIO().execute {
                        saveCallResult(response.data)
                        mExecutors.mainThread().execute {
                            result.addSource(loadFromDB()) { newData ->
                                result.value = Resource.Success(newData)
                            }
                        }
                    }
                is ApiResponse.Empty -> mExecutors.mainThread().execute {
                    result.addSource(loadFromDB()) { newData ->
                        result.value = Resource.Success(newData)
                    }
                }
                is ApiResponse.Error -> {
                    onFetchFailed(response.errorException)
                    result.addSource(dbSource) { newData ->
                        result.value = Resource.Error(response.errorException.message ?: "",
                            response.errorException, newData)
                    }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> = result

    fun asMutableLiveData(): MutableLiveData<Resource<ResultType>> = result
}