package com.example.elfatehgroup.ui.main.viewmodel

import android.util.Log
import com.example.elfatehgroup.ui.main.state.MainStateEvent
import com.example.elfatehgroup.ui.main.state.MainViewState
import com.google.gson.internal.LazilyParsedNumber

fun MainViewModel.resetPageNumber(){
    setIsQueryExhausted(false)
    setIsQueryInProgress(false)
    setPageNumber(1)
    setSearchQuery("")
}

fun MainViewModel.loadFirstPage(){
    resetPageNumber()
    setIsQueryInProgress(true)
    setStateEvent(MainStateEvent.GetProductsListEvent())
}

private fun MainViewModel.loadPage(pageNumber:Int){
    Log.e("MainViewModel","""loadPage: getIsQueryExhausted:${getIsQueryExhausted()}""")
    Log.e("MainViewModel","""loadPage: , getIsQueryInProgress: ${getIsQueryInProgress()}""")
    if (!getIsQueryExhausted() && !getIsQueryInProgress()){
        Log.e("MainViewModel","loadNextPage: loading next page...")
        setPageNumber(pageNumber)
        setIsQueryInProgress(true)
        setStateEvent(MainStateEvent.GetProductsListEvent())

    }

}

fun MainViewModel.loadNextPage(){
    loadPage(getPageNumber() +1)
}

fun MainViewModel.loadCurrentPage(){
    Log.e("MainViewModel" ,"loadCurrentPage")
   loadPage(getPageNumber())
}


fun MainViewModel.handelIncomingProductsListData(mainViewState: MainViewState){
    val productFields = mainViewState.productsFragmentsFields
    Log.e("MainViewModel","handelIncomingData: isQueryInProgress: ${productFields.isQueryInProgress}")
    Log.e("MainViewModel","handelIncomingData: isQueryExhausted: ${productFields.isQueryExhausted}")
    setIsQueryInProgress(productFields.isQueryInProgress)
    setIsQueryExhausted(productFields.isQueryExhausted)
    setProductsList(productFields.productList)

}

