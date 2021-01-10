package com.project.sehatqtest.data.repository

import androidx.lifecycle.LiveData
import com.project.sehatqtest.data.source.local.SharedPrefDataStore
import com.project.sehatqtest.data.source.remote.model.ProfileItem
import com.project.sehatqtest.domain.repository.IProfileRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(private val sharedPrefDataStore: SharedPrefDataStore)
    : IProfileRepository {

    override fun getProfile(): LiveData<ProfileItem?> {
         return sharedPrefDataStore.getMyProfile()
    }

    override fun getLoginStatus(): String {
        return sharedPrefDataStore.getLoginStatus()
    }
}