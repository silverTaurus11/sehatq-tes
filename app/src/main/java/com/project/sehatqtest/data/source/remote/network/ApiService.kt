package com.project.sehatqtest.data.source.remote.network

import com.project.sehatqtest.data.source.remote.model.HomeResponse
import retrofit2.Call
import retrofit2.http.GET

@JvmSuppressWildcards
interface ApiService {
    @GET("home")
    suspend fun getHomeData(): List<HomeResponse>
}