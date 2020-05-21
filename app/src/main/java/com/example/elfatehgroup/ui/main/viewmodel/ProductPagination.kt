package com.example.elfatehgroup.ui.main.viewmodel

import android.util.Log
import com.example.elfatehgroup.ui.main.state.MainStateEvent
import com.example.elfatehgroup.ui.main.state.MainViewState

fun MainViewModel.resetProductPageNumber(){
    setProductIsQueryExhausted(false)
    setProductIsQueryInProgress(false)
    setProductPageNumber(1)
    setProductSearchQuery("")
}

fun MainViewModel.loadProductFirstPage(){
    resetProductPageNumber()
    setProductIsQueryInProgress(true)
    setStateEvent(MainStateEvent.GetProductsListEvent())
}

private fun MainViewModel.loadProductPage(pageNumber:Int){
    Log.e("MainViewModel","""loadPage: getIsQueryExhausted:${getProductIsQueryExhausted()}""")
    Log.e("MainViewModel","""loadPage: , getIsQueryInProgress: ${getProductIsQueryInProgress()}""")
    if (!getProductIsQueryExhausted() && !getProductIsQueryInProgress()){
        Log.e("MainViewModel","loadNextPage: loading next page...")
        setProductPageNumber(pageNumber)
        setProductIsQueryInProgress(true)
        setStateEvent(MainStateEvent.GetProductsListEvent())

    }

}

fun MainViewModel.loadProductNextPage(){
    loadProductPage(getProductPageNumber() +1)
}

fun MainViewModel.loadProductCurrentPage(){
    Log.e("MainViewModel" ,"loadCurrentPage")
   loadProductPage(getProductPageNumber())
}


fun MainViewModel.handelIncomingProductsListData(mainViewState: MainViewState){
    val productFields = mainViewState.productsFragmentsFields
    Log.e("MainViewModel","handelIncomingData: isQueryInProgress: ${productFields.isQueryInProgress}")
    Log.e("MainViewModel","handelIncomingData: isQueryExhausted: ${productFields.isQueryExhausted}")
    setProductIsQueryInProgress(productFields.isQueryInProgress)
    setProductIsQueryExhausted(productFields.isQueryExhausted)
    setProductsList(productFields.productList)

}

