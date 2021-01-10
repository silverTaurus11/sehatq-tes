package com.project.sehatqtest.domain.repository

import androidx.lifecycle.LiveData
import com.project.sehatqtest.data.source.remote.model.ProfileItem

interface ILoginRepository {
    fun doLogin(item: ProfileItem, isRememberLogin: Boolean): LiveData<Boolean>
    fun isUserAlreadyLogin(): LiveData<Boolean>
}