package com.example.elfatehgroup.ui.main.viewmodel

import com.example.elfatehgroup.api.main.responses.Product

fun MainViewModel.setProductsList(newProductsList:List<Product>){
    val update = getCurrentViewStateOrNew()
    update.productsFragmentsFields.productList = newProductsList
    setViewState(update)
}