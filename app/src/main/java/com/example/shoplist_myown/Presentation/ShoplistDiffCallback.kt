package com.example.shoplist_myown.Presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.shoplist_myown.Domain.Shopitem

class ShoplistDiffCallback: DiffUtil.ItemCallback<Shopitem>() {
    override fun areItemsTheSame(oldItem: Shopitem, newItem: Shopitem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Shopitem, newItem: Shopitem): Boolean {
        return oldItem == newItem
    }

}