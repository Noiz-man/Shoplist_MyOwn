package com.example.shoplist_myown.Domain

data class Shopitem(
    val name:String,
    val count: Int,
    var state: Boolean,
    var id: Int = UNDEFINED_ID
) {
    companion object{
        val UNDEFINED_ID = -1
    }
}
