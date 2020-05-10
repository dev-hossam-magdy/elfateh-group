package com.example.elfatehgroup.di.modules.main

import com.example.elfatehgroup.api.main.ElfatehGroupApi
import com.example.elfatehgroup.di.annotations.MainScope
import com.example.elfatehgroup.persistance.AppDatabase
import com.example.elfatehgroup.persistance.dao.ProductDao
import com.example.elfatehgroup.repository.MainRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
object MainModule {


    @MainScope
    @Provides
    fun provideMainRepository(elfatehGroupApi: ElfatehGroupApi, productDao:ProductDao):MainRepository =
        MainRepository(elfatehGroupApi,productDao)

    @MainScope
    @Provides
    fun provideElfatehGroupApi(retrofitBuilder: Retrofit.Builder): ElfatehGroupApi =
        retrofitBuilder.build().create(ElfatehGroupApi::class.java)

    @MainScope
    @Provides
    fun provideProductsDao(appDatabase: AppDatabase): ProductDao =
        appDatabase.getProductsDao()


}