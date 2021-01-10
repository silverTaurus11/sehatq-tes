package com.project.sehatqtest.domain.usecase.logout

import com.project.sehatqtest.domain.repository.ILogoutRepository
import javax.inject.Inject

class LogoutInteractor @Inject constructor(private val logoutRepository: ILogoutRepository): LogoutUseCase {

    override fun logout() {
        logoutRepository.logout()
    }

    override fun isRememberUser(): Boolean {
        return logoutRepository.isRememberUser()
    }
}