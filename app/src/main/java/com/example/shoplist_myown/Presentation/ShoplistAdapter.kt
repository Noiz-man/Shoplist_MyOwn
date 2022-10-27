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

        var onShopitemClickListener: ((Shopitem) -> Unit)? = null
        var onShopitemLongClickListener: ((Shopitem) -> Unit)? = null

    class ShoplistViewHolder(itemView: View) : ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_name)
        val count = itemView.findViewById<TextView>(R.id.tv_Count)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoplistViewHolder {
        val state = if (viewType == ACTIVE) {
            R.layout.active_item
        } else R.layout.inactive_item
        val view = LayoutInflater.from(parent.context).inflate(state, parent, false)
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

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position).state) {
            true -> ACTIVE
            false -> INACTIVE
        }
    }

    companion object{
        val ACTIVE = 1
        val INACTIVE = 0
    }
}