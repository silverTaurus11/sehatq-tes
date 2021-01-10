package com.project.sehatqtest.view.favorite

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.sehatqtest.R
import com.project.sehatqtest.data.source.local.model.ProductEntity
import com.project.sehatqtest.databinding.ActivityFavoriteProductBinding
import com.project.sehatqtest.helper.IntentHelper
import com.project.sehatqtest.view.adapter.ProductItemListener
import com.project.sehatqtest.view.adapter.ProductsListAdapter
import com.project.sehatqtest.view.frontview.menu.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteProductActivity: AppCompatActivity(), ProductItemListener {


    private val homeViewModel: HomeViewModel by viewModels()

    private val productsListAdapter by lazy { ProductsListAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityFavoriteProductBinding = ActivityFavoriteProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.productRecyclerView.adapter = productsListAdapter
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.toolbar.setTitle(R.string.favorite_product)
        initFavoriteProductObserver()
    }

    private fun initFavoriteProductObserver(){
        homeViewModel.getFavoriteProducts().observe(this){
            productsListAdapter.submitList(it)
        }
    }

    override fun onItemClick(item: ProductEntity) {
        IntentHelper.launchProductDetail(item.id, this)
    }

    override fun onLikeClick(item: ProductEntity) {
        homeViewModel.updateFavoriteProductItem(item)
    }
}