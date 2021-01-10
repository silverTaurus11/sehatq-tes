package com.project.sehatqtest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.sehatqtest.R
import com.project.sehatqtest.data.source.local.model.ProductEntity
import com.project.sehatqtest.databinding.ProductItemBinding

class ProductsListAdapter(private val listener: ProductItemListener): PagedListAdapter<ProductEntity,
        ProductsListAdapter.ProductViewHolder>(
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

    class ProductViewHolder(val binding: ProductItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position) as ProductEntity
        holder.binding.productName.text = item.title
        holder.binding.productImage.apply {
            Glide.with(holder.itemView).load(item.imageUrl).into(this)
        }
        if(item.loved == 1){
            holder.binding.likeIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else{
            holder.binding.likeIcon.setImageResource(R.drawable.ic_outline_unfavorite_24)
        }
        holder.itemView.setOnClickListener {
            listener.onItemClick(item)
        }
        holder.binding.likeIcon.setOnClickListener {
            listener.onLikeClick(item)
        }
    }
}