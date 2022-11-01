package com.example.shoplist_myown.Presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoplist_myown.Data.ShoplistRepositoryImpl
import com.example.shoplist_myown.Domain.AddShopitemUseCase
import com.example.shoplist_myown.Domain.EditShopitemUseCase
import com.example.shoplist_myown.Domain.GetShopitemByID
import com.example.shoplist_myown.Domain.Shopitem

class NewShopitemViewModel : ViewModel() {

    val repositiry = ShoplistRepositoryImpl
    val addShopitemUseCase = AddShopitemUseCase(repositiry)
    val editShopitemUseCase = EditShopitemUseCase(repositiry)
    val getShopitemByID = GetShopitemByID(repositiry)

    val _shopitem = MutableLiveData<Shopitem>()
    val shopitem: LiveData<Shopitem>
        get() = _shopitem

    val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    val _closeWindow = MutableLiveData<Unit>()
    val closeWindow: LiveData<Unit>
        get() = _closeWindow


    fun addShopitem(name: String?, count: String?) {
        val parsename = parseName(name)
        val parseCount = parseCount(count)
        if (validData(parsename, parseCount)) {
            val item = Shopitem(parsename, parseCount, true)
            addShopitemUseCase.addShopitem(item)
        }
        closeWindow()
    }

    fun editAhopitem(name: String?, count: String?) {
        val parsename = parseName(name)
        val parseCount = parseCount(count)
        if (validData(parsename, parseCount)) {
            _shopitem.value?.let {
                val item = it.copy(parsename, parseCount)
                editShopitemUseCase.editShopitem(item)
            }
        }
        closeWindow()
    }

    fun getItemByID(id: Int) {
        _shopitem.value = getShopitemByID.getShopitemByID(id)
    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun parseCount(count: String?): Int {
        val result = try {
            count?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
        return result
    }

    private fun validData(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return false
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    fun closeWindow() {
        _closeWindow.value = Unit
    }

}

