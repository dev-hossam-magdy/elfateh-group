package com.example.elfatehgroup.di.modules.main.products

import com.bumptech.glide.RequestManager
import com.example.elfatehgroup.adapters.ProductAdapter
import com.example.elfatehgroup.di.annotations.ProductScope
import com.example.elfatehgroup.persistance.AppDatabase
import com.example.elfatehgroup.persistance.dao.ProductDao
import dagger.Module
import dagger.Provides

@Module
object ProductsModule {

    @ProductScope
    @Provides
    fun provideProductAdapter(requestManager: RequestManager): ProductAdapter =
        ProductAdapter(requestManager)

}