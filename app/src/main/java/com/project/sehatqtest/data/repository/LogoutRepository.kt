package com.project.sehatqtest.data.repository

import com.project.sehatqtest.data.AppExecutors
import com.project.sehatqtest.data.source.local.IS_REMEMBER_LOGIN
import com.project.sehatqtest.data.source.local.LocalDataStore
import com.project.sehatqtest.data.source.local.MY_PROFILE
import com.project.sehatqtest.data.source.local.SharedPrefDataStore
import com.project.sehatqtest.domain.repository.ILogoutRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogoutRepository @Inject constructor(
        private val sharedPrefDataStore: SharedPrefDataStore,
        private val localDataStore: LocalDataStore,
        private val appExecutors: AppExecutors): ILogoutRepository {

    override fun logout() {
        appExecutors.diskIO().execute {
            localDataStore.deleteCategories()
            localDataStore.deleteProducts()
            localDataStore.deletePurchaseHistory()
            sharedPrefDataStore.clearMyProfile()
            sharedPrefDataStore.saveData(IS_REMEMBER_LOGIN, false)
        }
    }

    override fun isRememberUser(): Boolean {
        return sharedPrefDataStore.getBooleanData(IS_REMEMBER_LOGIN)
    }
}