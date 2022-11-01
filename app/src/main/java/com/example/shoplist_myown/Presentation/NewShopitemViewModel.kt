package com.example.shoplist_myown.Presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoplist_myown.Data.ShoplistRepositoryImpl
import com.example.shoplist_myown.Domain.AddShopitemUseCase
import com.example.shoplist_myown.Domain.EditShopitemUseCase
import com.example.shoplist_myown.Domain.GetShopitemByID
import com.example.shoplist_myown.Domain.Shopitem

class NewShopitemViewModel: ViewModel() {

    val repositiry = ShoplistRepositoryImpl
    val addShopitemUseCase = AddShopitemUseCase(repositiry)
    val editShopitemUseCase = EditShopitemUseCase(repositiry)
    val getShopitemByID = GetShopitemByID(repositiry)

    val _shopitem = MutableLiveData<Shopitem>()
    val shopitem: LiveData<Shopitem>
        get() = _shopitem



    private fun addShopitem(name: String?, count: String?) {
        val parsename = parseName(name)
        val parseCount = parseCount(count)
        if (validData(parsename, parseCount)) {
            val item = Shopitem(parsename, parseCount, true)
            addShopitemUseCase.addShopitem(item)
        }
        // TODO закрыть окно
    }

    private fun editAhopitem(name: String?, count: String?) {
        val parsename = parseName(name)
        val parseCount = parseCount(count)
        if (validData(parsename, parseCount)) {
            _shopitem.value?.let {
                val item = it.copy(parsename, parseCount)
                editShopitemUseCase.editShopitem(item)
            }
        }
        // TODO закрыть окно
    }

    private fun getItemByID(id: Int){
        _shopitem.value = getShopitemByID.getShopitemByID(id)
    }

    private fun parseName(name: String?): String{
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
            // TODO ошибка ввода имени
            result = false
        }
        if (count <= 0) {
            // TODO ошибка ввода кол-ва
            result = false
        }
        return false
    }


}

