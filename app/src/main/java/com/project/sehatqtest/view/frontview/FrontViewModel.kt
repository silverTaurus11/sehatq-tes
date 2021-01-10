package com.project.sehatqtest.view.frontview

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.project.sehatqtest.domain.usecase.frontview.FrontViewUseCase
import com.project.sehatqtest.domain.usecase.home.HomeUseCase

class FrontViewModel @ViewModelInject constructor(private val frontViewUseCase: FrontViewUseCase): ViewModel() {

    val profileLiveData by lazy { frontViewUseCase.getProfile() }

    fun getLoginStatus() = frontViewUseCase.getLoginStatus()
}