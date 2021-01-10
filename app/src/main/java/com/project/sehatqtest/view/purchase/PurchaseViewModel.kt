package com.project.sehatqtest.view.purchase

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.project.sehatqtest.domain.usecase.purchase.PurchaseHistoryUseCase

class PurchaseViewModel @ViewModelInject constructor(private val purchaseHistoryUseCase: PurchaseHistoryUseCase): ViewModel() {

    fun getPurchaseHistory() = purchaseHistoryUseCase.getPurchaseHistory()

}