package com.example.elfatehgroup.di.modules.main.catalog

import androidx.room.Room
import com.bumptech.glide.RequestManager
import com.example.elfatehgroup.adapters.CatalogItemAdapter
import com.example.elfatehgroup.di.annotations.CatalogScope
import com.example.elfatehgroup.persistance.AppDatabase
import com.example.elfatehgroup.persistance.dao.CatalogItemDao
import dagger.Module
import dagger.Provides

@Module
object CatalogModule {

    @CatalogScope
    @Provides
    fun provideProductAdapter(requestManager: RequestManager): CatalogItemAdapter =
        CatalogItemAdapter(requestManager)

}