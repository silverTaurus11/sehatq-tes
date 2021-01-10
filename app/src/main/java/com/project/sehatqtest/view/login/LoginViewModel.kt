package com.project.sehatqtest.view.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.sehatqtest.data.source.remote.model.ProfileItem
import com.project.sehatqtest.domain.usecase.login.LoginUseCase
import com.project.sehatqtest.domain.usecase.logout.LogoutUseCase
import com.project.sehatqtest.helper.Utils

class LoginViewModel @ViewModelInject constructor(private val loginUseCase: LoginUseCase,
                                                  private val logoutUseCase: LogoutUseCase): ViewModel() {

    enum class Validation{
        USERNAME_INVALID,
        PASSWORD_INVALID,
        NONE
    }

    val formValidation = MutableLiveData<Validation>()

    init {
        formValidation.value = Validation.NONE
    }

    fun doLogin(profileItem: ProfileItem, isRememberLogin: Boolean): LiveData<Boolean> {
        val isSuccess = MutableLiveData<Boolean>()
        doFormValidation(profileItem).also {
            if(it){
                return loginUseCase.doLogin(profileItem, isRememberLogin)
            } else{
                isSuccess.value = false
            }
        }
        return isSuccess
    }

    fun doLoginWithThirdParty(profileItem: ProfileItem){
        loginUseCase.doLogin(profileItem, true)
    }

    val isUserAlreadyLogin by lazy { loginUseCase.isUserAlreadyLogin() }

    private fun doFormValidation(profileItem: ProfileItem): Boolean{
        val isUsernameValid = Utils.isUserNameValid(profileItem.userName)
        if(!isUsernameValid) formValidation.value = Validation.USERNAME_INVALID
        val isPasswordValid = Utils.isUserPasswordValid(profileItem.password)
        if(!isPasswordValid) formValidation.value = Validation.PASSWORD_INVALID
        return isUsernameValid && isPasswordValid
    }

    fun clearCache(){
        logoutUseCase.logout()
    }
}