package com.project.sehatqtest.domain.usecase.login

import androidx.lifecycle.LiveData
import com.project.sehatqtest.data.source.remote.model.ProfileItem

interface LoginUseCase {
    fun isUserAlreadyLogin(): LiveData<Boolean>
    fun doLogin(item: ProfileItem, isRememberLogin: Boolean): LiveData<Boolean>
}