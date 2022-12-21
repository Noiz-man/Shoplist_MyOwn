package com.example.shoplist_myown.Presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoplist_myown.Data.ShoplistRepositoryImpl
import com.example.shoplist_myown.Domain.DeleteShopitemUseCase
import com.example.shoplist_myown.Domain.EditShopitemUseCase
import com.example.shoplist_myown.Domain.GetShoplistUseCase
import com.example.shoplist_myown.Domain.Shopitem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ShoplistViewModel(application: Application): AndroidViewModel(application) {
    private val repositiry = ShoplistRepositoryImpl(application)


    val deleteShopitemUseCase = DeleteShopitemUseCase(repositiry)
    val editShopitemUseCase = EditShopitemUseCase(repositiry)
    val getShoplistUseCase = GetShoplistUseCase(repositiry)

    val shoplist = getShoplistUseCase.getShoplist()

    fun deleteShopitem(item: Shopitem) {
        viewModelScope.launch {
            deleteShopitemUseCase.deleteShopitem(item)
        }
    }

    fun editShopitemState(item: Shopitem) {
        val newItem = item.copy(state = !item.state)
        viewModelScope.launch {
            editShopitemUseCase.editShopitem(newItem)
        }
    }
}