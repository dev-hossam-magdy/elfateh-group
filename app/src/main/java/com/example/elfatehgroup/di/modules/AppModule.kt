package com.example.elfatehgroup.di.modules

import android.app.Application
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.blogapplication.util.LiveDataCallAdapterFactory
import com.example.elfatehgroup.R
import com.example.elfatehgroup.base.BaseApplication
import com.example.elfatehgroup.persistance.AppDatabase
import com.example.elfatehgroup.persistance.dao.ProductDao
import com.example.elfatehgroup.util.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object AppModule {

    @Singleton
    @Provides
    fun provideGsonBuilder():Gson =
        GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gson: Gson):Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())



    @Singleton
    @Provides
    fun provideRoomInstance(application: Application):AppDatabase =
        Room.databaseBuilder(application,AppDatabase::class.java,AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()



    @Singleton
    @Provides
    fun provideRequestOptions(): RequestOptions =
        RequestOptions()
            .placeholder(R.drawable.elfateh_logo)
            .error(R.drawable.elfateh_logo)
    @Singleton
    @Provides
    fun provideRequestManger(application: Application,requestOptions: RequestOptions): RequestManager =
        Glide.with(application)
            .setDefaultRequestOptions(requestOptions)
}