package com.example.elfatehgroup.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.elfatehgroup.api.main.responses.Product
import com.example.elfatehgroup.persistance.dao.ProductDao


@Database(entities = [Product::class],version = 1,exportSchema = false)
abstract class AppDatabase :RoomDatabase(){
    abstract fun getProductsDao():ProductDao

    companion object{
        const val DATABASE_NAME ="app_db"
    }


}
