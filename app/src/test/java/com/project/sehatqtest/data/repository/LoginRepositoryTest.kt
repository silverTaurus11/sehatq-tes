package com.project.sehatqtest.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.project.sehatqtest.data.source.local.LOGIN_VIA_NORMAL
import com.project.sehatqtest.data.source.local.SharedPrefDataStore
import com.project.sehatqtest.data.source.remote.model.ProfileItem
import com.project.sehatqtest.util.LiveDataTestUtil.observeForTesting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class LoginRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val sharedPrefDataStore = Mockito.mock(SharedPrefDataStore::class.java)
    private val loginRepository = LoginRepository(sharedPrefDataStore)

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun cleanUp() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun doLoginTest(){
        val profileItem = ProfileItem("lalala", "opopopo", LOGIN_VIA_NORMAL)
        val isSuccess = loginRepository.doLogin(profileItem, true)
        verify(sharedPrefDataStore).saveMyProfile(any())
        verify(sharedPrefDataStore).saveData(anyString(), anyBoolean())
        isSuccess.observeForTesting {
            assertEquals(true, isSuccess.value)
        }
    }

    @Test
    fun isUserAlreadyLogin_True(){
        Mockito.`when`(sharedPrefDataStore.getBooleanLiveData(any())).thenReturn(MutableLiveData<Boolean>().apply { value = true })
        val isRemember = loginRepository.isUserAlreadyLogin()
        verify(sharedPrefDataStore).getBooleanLiveData(any())
        isRemember.observeForTesting {
            assertEquals(true, isRemember.value)
        }
    }

    @Test
    fun isUserAlreadyLogin_False(){
        Mockito.`when`(sharedPrefDataStore.getBooleanLiveData(any())).thenReturn(MutableLiveData<Boolean>().apply { value = false })
        val isRemember = loginRepository.isUserAlreadyLogin()
        verify(sharedPrefDataStore).getBooleanLiveData(any())
        isRemember.observeForTesting {
            assertEquals(false, isRemember.value)
        }
    }

}