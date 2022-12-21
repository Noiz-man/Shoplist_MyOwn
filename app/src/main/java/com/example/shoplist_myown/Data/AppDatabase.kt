package com.example.shoplist_myown.Data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDbModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shopListDao(): DAO_shopitem

    companion object {

        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "dataBase.db"

        //        TODO если не будет работать сделать реализацию синглтона через double-check
        @Synchronized
        fun getInstance(application: Application): AppDatabase {
            INSTANCE?.let {
                return it
            }
            val db = (Room.databaseBuilder(application,
                AppDatabase::class.java,
                DB_NAME)).allowMainThreadQueries().build()
            INSTANCE = db
            return db
        }
    }
}