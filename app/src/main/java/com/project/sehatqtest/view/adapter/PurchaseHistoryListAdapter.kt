package com.project.sehatqtest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.sehatqtest.R
import com.project.sehatqtest.data.source.local.model.PurchaseHistoryEntity
import com.project.sehatqtest.databinding.PurchaseHistoryItemBinding
import com.project.sehatqtest.helper.Utils

class PurchaseHistoryListAdapter(private val onItemClick: (PurchaseHistoryEntity)-> Unit):
        PagedListAdapter<PurchaseHistoryEntity, PurchaseHistoryListAdapter.PurchaseHistoryViewHolder>(
                object : DiffUtil.ItemCallback<PurchaseHistoryEntity>(){
                    override fun areItemsTheSame(oldItem: PurchaseHistoryEntity, newItem: PurchaseHistoryEntity): Boolean {
                        return oldItem.id == newItem.id
                    }

                    override fun areContentsTheSame(oldItem: PurchaseHistoryEntity, newItem: PurchaseHistoryEntity): Boolean {
                        return oldItem == newItem
                    }
                }

        ) {

    class PurchaseHistoryViewHolder(val binding: PurchaseHistoryItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseHistoryViewHolder {
        val binding = PurchaseHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PurchaseHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PurchaseHistoryViewHolder, position: Int) {
        val item = getItem(position) as PurchaseHistoryEntity
        holder.binding.productImage.apply {
            Glide.with(holder.itemView).load(item.product?.imageUrl).into(this)
        }
        holder.binding.productName.text = item.product?.title
        holder.binding.productPrice.text = item.product?.price
        holder.binding.trxDate.text = holder.itemView.resources.getString(R.string.transaction_date_template)
                .replace("$1", Utils.convertMillisToDateString(item.dateInMillis))
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }
}