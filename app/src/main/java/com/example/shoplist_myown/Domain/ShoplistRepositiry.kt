package com.example.shoplist_myown.Domain

import androidx.lifecycle.LiveData

interface ShoplistRepositiry {
    fun addShopitem(item: Shopitem)
    fun deleteShopitem(item: Shopitem)
    fun editShopitem(item: Shopitem)
    fun getShopitemByID(id: Int): Shopitem
    fun getShoplist(): LiveData<List<Shopitem>>
}