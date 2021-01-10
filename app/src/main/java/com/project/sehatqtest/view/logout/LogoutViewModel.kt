package com.project.sehatqtest.view.logout

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.project.sehatqtest.domain.usecase.logout.LogoutUseCase

class LogoutViewModel @ViewModelInject constructor(private val logoutUseCase: LogoutUseCase): ViewModel() {

    fun logout(){
        logoutUseCase.logout()
    }

}