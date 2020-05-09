package com.example.elfatehgroup.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.elfatehgroup.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindsAuthViewModelFactory(factort: ViewModelProviderFactory):ViewModelProvider.Factory
}