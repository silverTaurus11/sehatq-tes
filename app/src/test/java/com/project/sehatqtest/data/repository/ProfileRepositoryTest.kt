package com.project.sehatqtest.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.verify
import com.project.sehatqtest.data.source.local.LOGIN_VIA_FACEBOOK
import com.project.sehatqtest.data.source.local.SharedPrefDataStore
import com.project.sehatqtest.data.source.remote.model.ProfileItem
import com.project.sehatqtest.util.LiveDataTestUtil.observeForTesting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class ProfileRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val sharedPrefDataStore = Mockito.mock(SharedPrefDataStore::class.java)
    private val profileRepository = ProfileRepository(sharedPrefDataStore)
    private val profileItem = ProfileItem("lalala", "kokoko", LOGIN_VIA_FACEBOOK, "ijijjsdsd")

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun cleanUp() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun getProfile(){
        Mockito.`when`(sharedPrefDataStore.getMyProfile()).thenReturn(MutableLiveData<ProfileItem>().apply { value = profileItem })
        val myProfileLiveData = profileRepository.getProfile()
        verify(sharedPrefDataStore).getMyProfile()
        myProfileLiveData.observeForTesting {
            val liveDataValue = myProfileLiveData.value!!
            Assert.assertEquals(liveDataValue.userName, profileItem.userName)
            Assert.assertEquals(liveDataValue.password, profileItem.password)
            Assert.assertEquals(liveDataValue.loginWith, profileItem.loginWith)
            Assert.assertEquals(liveDataValue.tokenThirdParty, profileItem.tokenThirdParty)
        }
    }

    @Test
    fun getLoginStatus(){
        Mockito.`when`(sharedPrefDataStore.getLoginStatus()).thenReturn(profileItem.loginWith)
        val loginStatus = profileRepository.getLoginStatus()
        verify(sharedPrefDataStore).getLoginStatus()
        Assert.assertEquals(loginStatus, profileItem.loginWith)
    }

}