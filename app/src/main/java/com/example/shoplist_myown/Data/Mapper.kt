package com.example.shoplist_myown.Data

import com.example.shoplist_myown.Domain.Shopitem

class Mapper {

    fun mapEntityToShopitemDB (shopitem: Shopitem) = ShopItemDbModel (
        id = shopitem.id,
        name = shopitem.name,
        count = shopitem.count,
        state = shopitem.state
            )

    fun mapShopitemDBtoEntity(shopItemDbModel: ShopItemDbModel) = Shopitem (
        id = shopItemDbModel.id,
        name = shopItemDbModel.name,
        count = shopItemDbModel.count,
        state = shopItemDbModel.state
            )

    fun mapListDBToListEntity(list: List<ShopItemDbModel>) = list.map {
        mapShopitemDBtoEntity(it)
    }

}