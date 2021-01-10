package com.project.sehatqtest.view.frontview.menu.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.project.sehatqtest.R
import com.project.sehatqtest.data.source.Resource
import com.project.sehatqtest.data.source.local.model.ProductEntity
import com.project.sehatqtest.databinding.HomeFragmentBinding
import com.project.sehatqtest.helper.EspressoIdlingResource
import com.project.sehatqtest.helper.HorizontalDividerItemDecoration
import com.project.sehatqtest.helper.IntentHelper
import com.project.sehatqtest.helper.viewBinding
import com.project.sehatqtest.view.adapter.CategoriesListAdapter
import com.project.sehatqtest.view.adapter.ProductItemListener
import com.project.sehatqtest.view.adapter.ProductsListAdapter
import com.project.sehatqtest.view.favorite.FavoriteProductActivity
import com.project.sehatqtest.view.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint
import java.net.UnknownHostException

@AndroidEntryPoint
class HomeFragment: Fragment(R.layout.home_fragment), ProductItemListener {
    private val homeViewModel: HomeViewModel by viewModels()
    private val binding by viewBinding(HomeFragmentBinding::bind)

    private val categoriesListAdapter by lazy { CategoriesListAdapter {
        Toast.makeText(requireContext(), it.name, Toast.LENGTH_SHORT).show()
    } }
    private val productsListAdapter by lazy { ProductsListAdapter(this) }
    private var isErrorToastAllowed = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(binding)
        initCategoryObserver()
        initProductObserver()
        binding.iconHeart.setOnClickListener {
            startActivity(Intent(requireContext(), FavoriteProductActivity::class.java))
        }
        binding.searchBox.setOnClickListener {
            startActivity(Intent(requireContext(), SearchActivity::class.java))
        }
        binding.swipeToRefresh.apply{
            setOnRefreshListener {
                isErrorToastAllowed = true
                homeViewModel.productsLiveData.refresh()
                Handler(Looper.getMainLooper()).postDelayed({
                    isRefreshing = false
                },300)
            }
        }
    }

    private fun initRecyclerView(binding: HomeFragmentBinding){
        binding.categoryRecyclerview.adapter = categoriesListAdapter
        binding.categoryRecyclerview.addItemDecoration(HorizontalDividerItemDecoration(resources.getDimensionPixelSize(R.dimen.divider_size)))
        binding.productRecyclerView.adapter = productsListAdapter
    }

    private fun initCategoryObserver(){
        homeViewModel.getCategories().observe(viewLifecycleOwner){
            categoriesListAdapter.submitList(it)
        }
    }

    private fun initProductObserver(){
        EspressoIdlingResource.increment()
        homeViewModel.productsLiveData.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    productsListAdapter.submitList(it.data)
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }
                is Resource.Error -> {
                    if(!it.data.isNullOrEmpty()){
                        productsListAdapter.submitList(it.data)
                    }
                    if(isErrorToastAllowed){
                        if(it.throwable is UnknownHostException){
                            Toast.makeText(requireContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        isErrorToastAllowed = false
                    }

                }
                else -> return@observe
            }
        }
    }

    override fun onItemClick(item: ProductEntity) {
        IntentHelper.launchProductDetail(item.id, requireContext())
    }

    override fun onLikeClick(item: ProductEntity) {
        homeViewModel.updateFavoriteProductItem(item)
    }
}