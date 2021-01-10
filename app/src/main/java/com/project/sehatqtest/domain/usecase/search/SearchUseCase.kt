package com.project.sehatqtest.domain.usecase.search

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.project.sehatqtest.data.source.local.model.ProductEntity

interface SearchUseCase {
    fun searchUseCase(query: String): LiveData<PagedList<ProductEntity>>
}