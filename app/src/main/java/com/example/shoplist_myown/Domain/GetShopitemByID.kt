package com.example.shoplist_myown.Domain

class GetShopitemByID(private val shoplistRepositiry: ShoplistRepositiry) {

    fun getShopitemByID(id: Int): Shopitem {
        return shoplistRepositiry.getShopitemByID(id)
    }
}