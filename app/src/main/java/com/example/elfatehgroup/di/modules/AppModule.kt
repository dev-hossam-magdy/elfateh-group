package com.example.elfatehgroup.di.modules

import com.example.blogapplication.util.LiveDataCallAdapterFactory
import com.example.elfatehgroup.util.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object AppModule {

    @Provides
    fun provideGsonBuilder():Gson =
        GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

    @Provides
    fun provideRetrofitBuilder(gson: Gson):Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())


}