package com.example.shoplist_myown.Domain

import androidx.lifecycle.LiveData

class GetShoplistUseCase(private val shoplistRepositiry: ShoplistRepositiry) {

    fun getShoplist(): LiveData<List<Shopitem>> {
        return shoplistRepositiry.getShoplist()
    }
}