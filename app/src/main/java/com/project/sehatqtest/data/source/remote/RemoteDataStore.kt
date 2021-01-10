package com.project.sehatqtest.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.project.sehatqtest.data.source.remote.model.HomeResponse
import com.project.sehatqtest.data.source.remote.network.ApiResponse
import com.project.sehatqtest.data.source.remote.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataStore @Inject constructor(private val apiService: ApiService){

    fun getRemoteData(): LiveData<ApiResponse<HomeResponse>> {
        return flow {
            try {
                val response = apiService.getHomeData()
                if (response.isNotEmpty()){
                    emit(ApiResponse.Success(response[0]))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO).asLiveData()
    }
}