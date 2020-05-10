package com.example.elfatehgroup.di

import android.app.Application
import com.example.elfatehgroup.base.BaseApplication
import com.example.elfatehgroup.di.modules.ActivityBuilderModule
import com.example.elfatehgroup.di.modules.AppModule
import com.example.elfatehgroup.di.modules.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityBuilderModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}