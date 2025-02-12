package com.example.elfatehgroup.persistance.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.elfatehgroup.api.main.responses.Product
import com.example.elfatehgroup.util.Constants

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(productsList: List<Product>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(productsList: Product): Long

    @Query(
        """
        SELECT * FROM products_table
        LIMIT(:pageNumber * :pageSize)"""
    )
    fun getProductsList(
        pageNumber: Int,
        pageSize: Int = Constants.DEFAULT_PRODUCT_PAGE_SIZE
    ): LiveData<List<Product>>


    @Query(
        """
        SELECT * FROM products_table 
        WHERE product_des LIKE '%' || :query || '%'
        OR product_title LIKE '%' || :query || '%'
        """
    )
    fun filterProducts(
        query:String
    ): LiveData<List<Product>>

}