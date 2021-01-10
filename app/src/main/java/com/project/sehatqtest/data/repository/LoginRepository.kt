package com.project.sehatqtest.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.sehatqtest.data.source.local.IS_REMEMBER_LOGIN
import com.project.sehatqtest.data.source.local.SharedPrefDataStore
import com.project.sehatqtest.data.source.remote.model.ProfileItem
import com.project.sehatqtest.domain.repository.ILoginRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
    private val sharedPrefDataStore: SharedPrefDataStore
): ILoginRepository {

    override fun doLogin(item: ProfileItem, isRememberLogin: Boolean): LiveData<Boolean> {
        val isLoginSuccess = MutableLiveData<Boolean>()
        try{
            sharedPrefDataStore.saveMyProfile(item)
            sharedPrefDataStore.saveData(IS_REMEMBER_LOGIN, isRememberLogin)
            isLoginSuccess.value = true
        } catch (e: Exception){
            isLoginSuccess.value = false
        }
        return isLoginSuccess
    }

    override fun isUserAlreadyLogin(): LiveData<Boolean> {
        return sharedPrefDataStore.getBooleanLiveData(IS_REMEMBER_LOGIN)
    }
}