package com.example.elfatehgroup.di.modules.main

import com.example.elfatehgroup.di.annotations.CatalogScope
import com.example.elfatehgroup.di.annotations.HomeScope
import com.example.elfatehgroup.di.annotations.ProductScope
import com.example.elfatehgroup.ui.main.fragments.CatalogFragment
import com.example.elfatehgroup.ui.main.fragments.HomeFragment
import com.example.elfatehgroup.ui.main.fragments.ProductsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {


    @HomeScope
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ProductScope
    @ContributesAndroidInjector
    abstract fun contributeProductsFragment(): ProductsFragment

    @CatalogScope
    @ContributesAndroidInjector
    abstract fun contributeCatalogFragment(): CatalogFragment
}