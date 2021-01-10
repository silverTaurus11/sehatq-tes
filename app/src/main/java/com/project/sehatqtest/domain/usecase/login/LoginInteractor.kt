package com.project.sehatqtest.domain.usecase.login

import androidx.lifecycle.LiveData
import com.project.sehatqtest.data.source.remote.model.ProfileItem
import com.project.sehatqtest.domain.repository.ILoginRepository
import javax.inject.Inject

class LoginInteractor @Inject constructor(private val loginRepository: ILoginRepository): LoginUseCase {

    override fun isUserAlreadyLogin(): LiveData<Boolean> {
        return loginRepository.isUserAlreadyLogin()
    }

    override fun doLogin(item: ProfileItem, isRememberLogin: Boolean): LiveData<Boolean> {
        return loginRepository.doLogin(item, isRememberLogin)
    }
}