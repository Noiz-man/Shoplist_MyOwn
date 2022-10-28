package com.example.shoplist_myown.Presentation

import androidx.lifecycle.ViewModel
import com.example.shoplist_myown.Data.ShoplistRepositoryImpl
import com.example.shoplist_myown.Domain.AddShopitemUseCase
import com.example.shoplist_myown.Domain.EditShopitemUseCase
import com.example.shoplist_myown.Domain.GetShopitemByID
import com.example.shoplist_myown.Domain.Shopitem

class NewShopitemViewModel: ViewModel() {
    private val repositiry = ShoplistRepositoryImpl

    val addShopitemUseCase = AddShopitemUseCase(repositiry)
    val editShopitemUseCase = EditShopitemUseCase(repositiry)
    val getShopitemByID = GetShopitemByID(repositiry)

    fun addShopitem(inputname: String?, inputcount: String?) {
        val name = parseName(inputname)
        val count = parseCount(inputcount)
        val validData = validateData(name, count)
        if (validData) {
            val item = Shopitem(name, count, true)
            addShopitemUseCase.addShopitem(item)
        }
    }

    fun editShopitem(inputname: String?, inputcount: String?) {
        val name = parseName(inputname)
        val count = parseCount(inputcount)
        val validData = validateData(name, count)
        if (validData) {
            val item = Shopitem(name, count, true)
            editShopitemUseCase.editShopitem(item)
        }
    }

    private fun parseName(string: String?): String {
        return string?.trim() ?: ""
    }

    private fun parseCount(string: String?): Int {
        return try { string?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateData(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            // TODO: ошибка ввода имени
            result = false
        }
        if (count <= 0) {
            // TODO: ошибка ввода количества
            result = false
        }
        return result
    }
}