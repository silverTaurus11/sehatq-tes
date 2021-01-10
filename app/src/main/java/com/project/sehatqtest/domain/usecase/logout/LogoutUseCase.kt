package com.project.sehatqtest.domain.usecase.logout

import androidx.lifecycle.LiveData

interface LogoutUseCase {
    fun logout()
    fun isRememberUser(): Boolean
}