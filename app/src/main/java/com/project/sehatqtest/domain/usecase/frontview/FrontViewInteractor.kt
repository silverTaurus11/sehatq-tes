package com.project.sehatqtest.domain.usecase.frontview

import androidx.lifecycle.LiveData
import com.project.sehatqtest.data.source.remote.model.ProfileItem
import com.project.sehatqtest.domain.repository.IProfileRepository
import javax.inject.Inject

class FrontViewInteractor @Inject constructor(val profileRepository: IProfileRepository): FrontViewUseCase {
    override fun getProfile(): LiveData<ProfileItem?> {
        return profileRepository.getProfile()
    }

    override fun getLoginStatus(): String {
        return profileRepository.getLoginStatus()
    }
}