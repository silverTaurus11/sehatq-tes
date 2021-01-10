package com.project.sehatqtest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.sehatqtest.R
import com.project.sehatqtest.data.source.local.model.ProductEntity
import com.project.sehatqtest.databinding.SearchProductItemBinding

class SearchProductListAdapter(private val listener: ProductItemListener): PagedListAdapter<ProductEntity,
        SearchProductListAdapter.SearchViewHolder>(
            object : DiffUtil.ItemCallback<ProductEntity>(){
                override fun areItemsTheSame(
                    oldItem: ProductEntity,
                    newItem: ProductEntity
                ): Boolean = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: ProductEntity,
                    newItem: ProductEntity
                ): Boolean = oldItem == newItem

            }
        ) {

    class SearchViewHolder(val binding: SearchProductItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = SearchProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position) as ProductEntity
        Glide.with(holder.itemView).load(item.imageUrl).into(holder.binding.productImage)
        holder.binding.productName.text = item.title
        holder.binding.productPrice.text = item.price
        holder.itemView.setOnClickListener {
            listener.onItemClick(item)
        }
        holder.binding.likeIcon.setOnClickListener {
            listener.onLikeClick(item)
        }
        if(item.loved == 1){
            holder.binding.likeIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else{
            holder.binding.likeIcon.setImageResource(R.drawable.ic_outline_unfavorite_24)
        }
    }
}