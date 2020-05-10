package com.example.elfatehgroup.di.modules

import com.example.elfatehgroup.di.annotations.MainScope
import com.example.elfatehgroup.di.modules.main.FragmentBuilderModule
import com.example.elfatehgroup.di.modules.main.MainModule
import com.example.elfatehgroup.di.modules.main.MainViewModelModule
import com.example.elfatehgroup.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @MainScope
    @ContributesAndroidInjector(
        modules = [
        FragmentBuilderModule::class,
        MainModule::class,
        MainViewModelModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}