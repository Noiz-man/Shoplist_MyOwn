package com.example.shoplist_myown.Domain

class EditShopitemUseCase(private val shoplistRepositiry: ShoplistRepositiry) {

    suspend fun editShopitem(item: Shopitem) {
        shoplistRepositiry.editShopitem(item)
    }
}