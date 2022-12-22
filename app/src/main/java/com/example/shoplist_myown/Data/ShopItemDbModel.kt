package com.example.shoplist_myown.Data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shoplist_myown.Domain.Shopitem

@Entity(tableName = "shopItems_db")
data class ShopItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name:String,
    val count: Int,
    var state: Boolean,
)
