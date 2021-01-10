package com.project.sehatqtest.domain.repository

interface ILogoutRepository {
    fun logout()
    fun isRememberUser(): Boolean
}