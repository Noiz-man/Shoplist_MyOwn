package com.example.shoplist_myown.Domain

class AddShopitemUseCase(private val shoplistRepositiry: ShoplistRepositiry) {

    fun addShopitem(item: Shopitem) {
        shoplistRepositiry.addShopitem(item)
    }


}