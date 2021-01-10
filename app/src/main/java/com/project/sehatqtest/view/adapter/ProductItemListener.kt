package com.project.sehatqtest.view.adapter

import com.project.sehatqtest.data.source.local.model.ProductEntity

interface ProductItemListener{
    fun onItemClick(item: ProductEntity)
    fun onLikeClick(item: ProductEntity)
}