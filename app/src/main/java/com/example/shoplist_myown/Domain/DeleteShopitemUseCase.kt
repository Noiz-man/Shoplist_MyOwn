package com.example.shoplist_myown.Domain

class DeleteShopitemUseCase(private val shoplistRepositiry: ShoplistRepositiry) {

    suspend fun deleteShopitem(item: Shopitem) {
        shoplistRepositiry.deleteShopitem(item)
    }
}