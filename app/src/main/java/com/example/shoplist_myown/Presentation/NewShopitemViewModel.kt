package com.example.shoplist_myown.Presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoplist_myown.Data.ShoplistRepositoryImpl
import com.example.shoplist_myown.Domain.*

class NewShopitemViewModel(application: Application): AndroidViewModel(application) {
    private val repositiry = ShoplistRepositoryImpl(application)

    val addShopitemUseCase = AddShopitemUseCase(repositiry)
    val editShopitemUseCase = EditShopitemUseCase(repositiry)
    val getShopitemByIDUseCase = GetShopitemByID(repositiry)

    private val _errorName = MutableLiveData<Boolean>()
    val errorName: LiveData<Boolean>
        get() = _errorName

    private val _errorCount = MutableLiveData<Boolean>()
    val errorCount: LiveData<Boolean>
        get() = _errorCount

    private val _shopitem = MutableLiveData<Shopitem>()
    val shopitem: LiveData<Shopitem>
        get() = _shopitem

    private val _closeWindow = MutableLiveData<Unit>()
    val closeWindow: LiveData<Unit>
        get() = _closeWindow

    fun addShopitem(inputName: String?, inputCount: String?) {
        val name = parseInputName(inputName)
        val count = parseInputCount(inputCount)
        if (validData(name, count)) {
            val item = Shopitem(name, count, true)
            addShopitemUseCase.addShopitem(item)
            closeWindow()
        }
    }

    fun editShopitem(inputName: String?, inputCount: String?) {
        val name = parseInputName(inputName)
        val count = parseInputCount(inputCount)
        if (validData(name, count)) {
            _shopitem.value?.let {
                val item = it.copy(name = name, count = count)
                editShopitemUseCase.editShopitem(item)
                closeWindow()
            }
        }
    }

    fun getShopitemByID(id: Int) {
        val item = getShopitemByIDUseCase.getShopitemByID(id)
        _shopitem.value = item
    }

    private fun parseInputName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseInputCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    fun validData(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorName.value = true
            result = false
        }
        if (count <= 0) {
            _errorCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorName() {
        _errorName.value = false
    }

    fun resetErrorCount() {
        _errorCount.value = false
    }

    private fun closeWindow() {
        _closeWindow.value = Unit
    }

}