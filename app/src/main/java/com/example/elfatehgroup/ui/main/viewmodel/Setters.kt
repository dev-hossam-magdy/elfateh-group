package com.example.elfatehgroup.ui.main.viewmodel

import android.app.DownloadManager
import com.example.elfatehgroup.api.main.responses.CatalogItem
import com.example.elfatehgroup.api.main.responses.Product

fun MainViewModel.setProductsList(newProductsList:List<Product>){
    val update = getCurrentViewStateOrNew()
    update.productsFragmentsFields.productList = newProductsList
    setViewState(update)
}

fun MainViewModel.setCatalogsList(newCatalogList:List<CatalogItem>){
    val update = getCurrentViewStateOrNew()
    update.catalogFragmentsFields.catalogItemList = newCatalogList
    setViewState(update)
}


fun MainViewModel.setPageNumber(pageNumber: Int){
    val update = getCurrentViewStateOrNew()
    if (update.productsFragmentsFields.pageNumber == pageNumber)
        return
    update.productsFragmentsFields.pageNumber = pageNumber
    setViewState(update)
}

fun MainViewModel.setSearchQuery(query: String){
    val update = getCurrentViewStateOrNew()
    if (update.productsFragmentsFields.searchQuery.equals(query))
        return
    update.productsFragmentsFields.searchQuery = query
    setViewState(update)
}


fun MainViewModel.setIsQueryInProgress(isQueryInProgress: Boolean){
    val update = getCurrentViewStateOrNew()
    if (update.productsFragmentsFields.isQueryInProgress == isQueryInProgress)
        return
    update.productsFragmentsFields.isQueryInProgress = isQueryInProgress
    setViewState(update)
}



fun MainViewModel.setIsQueryExhausted(isQueryExhausted: Boolean){
    val update = getCurrentViewStateOrNew()
    if (update.productsFragmentsFields.isQueryExhausted == isQueryExhausted)
        return
    update.productsFragmentsFields.isQueryExhausted = isQueryExhausted
    setViewState(update)
}