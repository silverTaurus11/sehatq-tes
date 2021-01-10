package com.project.sehatqtest.view.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.sehatqtest.data.source.local.model.ProductEntity
import com.project.sehatqtest.databinding.ActivitySearchBinding
import com.project.sehatqtest.helper.IntentHelper
import com.project.sehatqtest.helper.Utils
import com.project.sehatqtest.view.adapter.ProductItemListener
import com.project.sehatqtest.view.adapter.SearchProductListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity: AppCompatActivity(), ProductItemListener{

    private val searchViewModel: SearchViewModel by viewModels()
    private val productsListAdapter by lazy { SearchProductListAdapter(this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySearchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.productRecyclerView.adapter = productsListAdapter
        binding.backButton.setOnClickListener { onBackPressed() }
        binding.searchBox.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.searchBox.text.toString().trim()
                doSearchProduct(query)
                Utils.hideSoftKeyboard(this)
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun doSearchProduct(query: String){
        searchViewModel.searchProduct(query).observe(this){
            productsListAdapter.submitList(it)
        }
    }

    override fun onItemClick(item: ProductEntity) {
        IntentHelper.launchProductDetail(item.id, this)
    }

    override fun onLikeClick(item: ProductEntity) {
        searchViewModel.updateFavoriteProduct(item)
    }


}