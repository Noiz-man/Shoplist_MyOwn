package com.example.shoplist_myown.Presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoplist_myown.Data.ShoplistRepositoryImpl
import com.example.shoplist_myown.Domain.DeleteShopitemUseCase
import com.example.shoplist_myown.Domain.EditShopitemUseCase
import com.example.shoplist_myown.Domain.GetShoplistUseCase
import com.example.shoplist_myown.Domain.Shopitem

class ShoplistViewModel: ViewModel() {
    private val repositiry = ShoplistRepositoryImpl


    val deleteShopitemUseCase = DeleteShopitemUseCase(repositiry)
    val editShopitemUseCase = EditShopitemUseCase(repositiry)
    val getShoplistUseCase = GetShoplistUseCase(repositiry)

    var shoplist = getShoplistUseCase.getShoplist()

    fun deleteShopitem(item: Shopitem) {
        deleteShopitemUseCase.deleteShopitem(item)
    }

    fun editShopitemState(item: Shopitem) {
        val newItem = item.copy(state = !item.state)
        editShopitemUseCase.editShopitem(newItem)
    }
}