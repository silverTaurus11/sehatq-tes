package com.project.sehatqtest.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.project.sehatqtest.data.AppExecutors
import com.project.sehatqtest.data.source.local.LocalDataStore
import com.project.sehatqtest.data.source.local.SharedPrefDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mockito
import org.mockito.Spy
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@ExperimentalCoroutinesApi
class LogoutRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val appExecutors = Mockito.mock(AppExecutors::class.java)
    private val localDataStore = Mockito.mock(LocalDataStore::class.java)
    private val sharedPrefDataStore = Mockito.mock(SharedPrefDataStore::class.java)
    private val logoutRepository = LogoutRepository(
        sharedPrefDataStore,
        localDataStore,
        appExecutors
    )

    @Spy
    private val executor: ExecutorService = Executors.newFixedThreadPool(3)

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun cleanUp() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun logout(){
        Mockito.`when`(appExecutors.diskIO()).thenReturn(executor)
        logoutRepository.logout()
    }

    @Test
    fun isRememberLogin_True(){
        Mockito.`when`(sharedPrefDataStore.getBooleanData(any())).thenReturn(true)
        val isRemember = logoutRepository.isRememberUser()
        verify(sharedPrefDataStore).getBooleanData(any())
        Assert.assertEquals(true, isRemember)
    }

    @Test
    fun isRememberLogin_False(){
        Mockito.`when`(sharedPrefDataStore.getBooleanData(any())).thenReturn(false)
        val isRemember = logoutRepository.isRememberUser()
        verify(sharedPrefDataStore).getBooleanData(any())
        Assert.assertEquals(false, isRemember)
    }

}