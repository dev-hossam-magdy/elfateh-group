package com.example.elfatehgroup.di.modules

import com.example.elfatehgroup.di.annotations.MainScope
import com.example.elfatehgroup.di.modules.main.FragmentBuilderModule
import com.example.elfatehgroup.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @MainScope
    @ContributesAndroidInjector(
        modules = [
        FragmentBuilderModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}