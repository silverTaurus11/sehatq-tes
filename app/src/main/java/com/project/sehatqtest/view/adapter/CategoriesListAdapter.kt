package com.project.sehatqtest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.sehatqtest.data.source.local.model.CategoryEntity
import com.project.sehatqtest.databinding.CategoryItemBinding

class CategoriesListAdapter(private val onItemClick: (CategoryEntity) -> Unit): PagedListAdapter<CategoryEntity,
        CategoriesListAdapter.CategoryViewHolder>(
        object : DiffUtil.ItemCallback<CategoryEntity>() {
            override fun areItemsTheSame(
                oldItem: CategoryEntity,
                newItem: CategoryEntity
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CategoryEntity,
                newItem: CategoryEntity
            ): Boolean = oldItem == newItem
        }
    ){

    class CategoryViewHolder(val binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = getItem(position) as CategoryEntity
        holder.binding.categoryImage.apply {
            Glide.with(holder.itemView).load(item.imageUrl).fitCenter().into(this)
        }
        holder.binding.categoryName.text = item.name
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }
}