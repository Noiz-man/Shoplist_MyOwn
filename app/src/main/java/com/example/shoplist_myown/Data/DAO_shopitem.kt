package com.example.shoplist_myown.Data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DAO_shopitem {

    @Query("SELECT * FROM shopItems_db")
    fun getShopListDb(): LiveData<List<ShopItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopItemDb(shopItemDbModel: ShopItemDbModel)

//    TODO попробовать сдеать аннотацию @Delete
    @Query("DELETE FROM shopItems_db WHERE id=:shopItemDbID")
    fun deleteShopItemDb(shopItemDbID: Int)

    @Query("SELECT * FROM shopItems_db WHERE id=:shopItemDbID LIMIT 1")
    fun getShopItemByID(shopItemDbID: Int): ShopItemDbModel
}