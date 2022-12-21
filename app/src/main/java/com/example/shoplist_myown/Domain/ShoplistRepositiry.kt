package com.example.shoplist_myown.Domain

import androidx.lifecycle.LiveData

interface ShoplistRepositiry {
    suspend fun addShopitem(item: Shopitem)
    suspend fun deleteShopitem(item: Shopitem)
    suspend fun editShopitem(item: Shopitem)
    suspend fun getShopitemByID(id: Int): Shopitem
    fun getShoplist(): LiveData<List<Shopitem>>
}