package com.example.shoplist_myown.Presentation

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoplist_myown.Domain.Shopitem
import com.example.shoplist_myown.R

class ShoplistAdapter: ListAdapter<Shopitem, ShoplistAdapter.ShoplistViewHolder>(ShoplistDiffCallback()) {

        val onShopitemClickListener: ((Shopitem) -> Unit)? = null
        val onShopitemLongClickListener: ((Shopitem) -> Unit)? = null

    class ShoplistViewHolder(itemView: View) : ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_name)
        val count = itemView.findViewById<TextView>(R.id.tv_Count)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoplistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.active_item, parent, false)
        return ShoplistViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoplistViewHolder, position: Int) {
        val shopitem = getItem(position)
        holder.name.text = shopitem.name
        holder.count.text = shopitem.count.toString()
        holder.itemView.setOnClickListener {
            onShopitemClickListener?.invoke(shopitem)
        }
        holder.itemView.setOnLongClickListener {
            onShopitemLongClickListener?.invoke(shopitem)
            true
        }
    }
}