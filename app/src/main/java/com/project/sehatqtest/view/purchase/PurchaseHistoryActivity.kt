package com.project.sehatqtest.view.purchase

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.sehatqtest.R
import com.project.sehatqtest.databinding.ActivityPurchaseHistoryBinding
import com.project.sehatqtest.helper.IntentHelper
import com.project.sehatqtest.view.adapter.PurchaseHistoryListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PurchaseHistoryActivity: AppCompatActivity() {

    private val purchaseHistoryListAdapter by lazy { PurchaseHistoryListAdapter{
        IntentHelper.launchProductDetail(it.product?.id, this)
    } }

    private val purchaseViewModel: PurchaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityPurchaseHistoryBinding = ActivityPurchaseHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.toolbar.title = getString(R.string.purchase_history)
        binding.productRecyclerView.adapter = purchaseHistoryListAdapter
        initPurchaseHistoryObserver()
    }

    private fun initPurchaseHistoryObserver(){
        purchaseViewModel.getPurchaseHistory().observe(this){
            purchaseHistoryListAdapter.submitList(it)
        }
    }
}