package com.example.elfatehgroup.ui.main.viewmodel

import android.util.Log
import com.example.elfatehgroup.ui.main.state.MainStateEvent
import com.example.elfatehgroup.ui.main.state.MainViewState

fun MainViewModel.resetCatalogPage(){
    Log.e("MainViewModel","resetCatalogPage")
    setCatalogIsQueryExhausted(false)
    setCatalogIsQueryInProgress(false)
    setCatalogSearchQuery("")
    setCatalogPageNumber(1)
}

fun MainViewModel.loadCatalogFirstPage(){
    Log.e("MainViewModel","loadCatalogFirstPage")
    resetCatalogPage()
    setCatalogIsQueryInProgress(true)
    setStateEvent(MainStateEvent.GetCatalogListEvent())
}

fun MainViewModel.loadCatalogPage(pageNumber: Int){
    Log.e("MainViewModel","loadCatalogNextPage:")
    Log.e("MainViewModel","loadCatalogNextPage: getCatalogIsQueryExhausted: ${getCatalogIsQueryExhausted()}")
    Log.e("MainViewModel","loadCatalogNextPage: getCatalogIsQueryInProgress: ${getCatalogIsQueryInProgress()}")

    if (!getCatalogIsQueryExhausted() && !getCatalogIsQueryInProgress()){
        setCatalogPageNumber(pageNumber)
        setCatalogIsQueryInProgress(true)
        setStateEvent(MainStateEvent.GetCatalogListEvent())
    }

}

fun MainViewModel.loadCatalogNextPage(){
    Log.e("MainViewModel","loadCatalogNextPage")
    loadCatalogPage(getCatalogPageNumber()+1)
}

fun MainViewModel.loadCatalogCurrentPage(){
    Log.e("MainViewModel","loadCatalogCurrentPage")
    loadCatalogPage(getCatalogPageNumber())
}

fun MainViewModel.handelIncomingCatalogListData(mainViewState: MainViewState){
    Log.e("MainViewModel","handelIncomingCatalogListData:")
    val catalogFields = mainViewState.catalogFragmentsFields
    Log.e("MainViewModel","handelIncomingCatalogListData: isQueryInProgress: ${catalogFields.isQueryInProgress}")
    Log.e("MainViewModel","handelIncomingCatalogListData: isQueryExhausted: ${catalogFields.isQueryExhausted}")
    Log.e("MainViewModel","handelIncomingCatalogListData: catalogItemList size : ${catalogFields.catalogItemList.size}")
    setCatalogIsQueryInProgress(catalogFields.isQueryInProgress)
    setCatalogIsQueryExhausted(catalogFields.isQueryExhausted)
    setCatalogsList(catalogFields.catalogItemList)
}