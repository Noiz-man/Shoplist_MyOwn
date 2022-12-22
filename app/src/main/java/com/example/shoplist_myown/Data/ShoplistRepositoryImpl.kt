package com.example.shoplist_myown.Data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.shoplist_myown.Domain.Shopitem
import com.example.shoplist_myown.Domain.ShoplistRepositiry
import kotlin.random.Random

class ShoplistRepositoryImpl(application: Application) : ShoplistRepositiry {

    val shoplistDAO = AppDatabase.getInstance(application).shopListDao()
    val mapper = Mapper()

    override suspend fun addShopitem(item: Shopitem) {
        shoplistDAO.addShopItemDb(mapper.mapEntityToShopitemDB(item))
    }

    override suspend fun deleteShopitem(item: Shopitem) {
        shoplistDAO.deleteShopItemDb(item.id)
    }

    override suspend fun editShopitem(item: Shopitem) {
        shoplistDAO.addShopItemDb(mapper.mapEntityToShopitemDB(item))
    }

    override suspend fun getShopitemByID(id: Int): Shopitem {
        return mapper.mapShopitemDBtoEntity(shoplistDAO.getShopItemByID(id))
    }

    override fun getShoplist(): LiveData<List<Shopitem>> =
        Transformations.map(shoplistDAO.getShopListDb()) {
            mapper.mapListDBToListEntity(it)
        }
}