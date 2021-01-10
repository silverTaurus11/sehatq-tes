package com.project.sehatqtest.domain.usecase.frontview

import androidx.lifecycle.LiveData
import com.project.sehatqtest.data.source.remote.model.ProfileItem

interface FrontViewUseCase {
    fun getProfile(): LiveData<ProfileItem?>
    fun getLoginStatus(): String
}