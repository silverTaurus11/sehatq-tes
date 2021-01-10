package com.project.sehatqtest.domain.repository

import androidx.lifecycle.LiveData
import com.project.sehatqtest.data.source.remote.model.ProfileItem

interface IProfileRepository {
    fun getProfile(): LiveData<ProfileItem?>
    fun getLoginStatus(): String
}