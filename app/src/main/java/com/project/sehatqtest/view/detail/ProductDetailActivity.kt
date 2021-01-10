package com.project.sehatqtest.view.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.project.sehatqtest.R
import com.project.sehatqtest.databinding.ActivityDetailProductBinding
import com.project.sehatqtest.helper.IntentHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailActivity: AppCompatActivity() {

    companion object{
        const val PRODUCT_ID_KEY = "productIdKey"
    }

    private val productDetailViewModel: ProductDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailProductBinding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        initProductObserver(binding)
    }

    private fun initProductObserver(binding: ActivityDetailProductBinding){
        productDetailViewModel.getProduct(intent.getIntExtra(PRODUCT_ID_KEY, 0)).observe(this){product ->
            val subject = "My Product"
            val body = StringBuilder("Detail Product")
                    .append("\nProduct Name : ${product.title}")
                    .append("\nProduct Price : ${product.price}").toString()

            Glide.with(this).load(product.imageUrl).into(binding.productBanner)
            Glide.with(this).load(product.imageUrl).into(binding.productImage)
            binding.productName.text = product.title
            binding.productDescription.text = product.description
            binding.likeIcon.apply {
                setOnClickListener {
                    productDetailViewModel.updateFavoriteProduct(product)
                }
                setImageResource(if(product.loved == 1) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_outline_unfavorite_24)
            }
            binding.productPrice.text = product.price
            binding.buyButton.setOnClickListener {
                productDetailViewModel.insertToPurchaseHistory(product)
                finish()
            }
            binding.shareButton.setOnClickListener {
                IntentHelper.shareContent(subject, body, this)
            }
        }
    }
}