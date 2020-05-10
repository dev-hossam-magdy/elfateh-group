package com.example.elfatehgroup.di.modules.main

import androidx.lifecycle.ViewModel
import com.codingwithmitch.openapi.di.auth.keys.ViewModelKey
import com.example.elfatehgroup.ui.main.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel):ViewModel
}

