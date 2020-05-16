package com.example.elfatehgroup.persistance.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.elfatehgroup.api.main.responses.CatalogItem
import com.example.elfatehgroup.util.Constants


@Dao
interface CatalogItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCatalogItem(catalogItem: CatalogItem): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfCatalogItems(list: List<CatalogItem>)

    @Query(
        """
        SELECT * FROM CATALOG_TABLE
        LIMIT(:pageNumber * :defaultPageSize)
        """
    )
    fun selectListOfCatalogItems(
        pageNumber: Int,
        defaultPageSize: Int = Constants.DEFULT_CATAOG_ITEM_PAGE_SIZE
    ): LiveData<List<CatalogItem>>


    @Query(
        """
        SELECT * FROM catalog_table
        WHERE yarn_name LIKE '%'|| :query ||'%'
    """
    )
    fun filterCatalogItems(
        query: String
    ): LiveData<List<CatalogItem>>

}