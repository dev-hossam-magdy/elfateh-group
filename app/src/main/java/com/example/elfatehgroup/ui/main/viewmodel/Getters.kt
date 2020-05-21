package com.example.elfatehgroup.ui.main.viewmodel

/////////////////////// Product  var //////////////////

fun MainViewModel.getProductPageNumber():Int =
    getCurrentViewStateOrNew().productsFragmentsFields.pageNumber

fun MainViewModel.getProductIsQueryInProgress():Boolean =
    getCurrentViewStateOrNew().productsFragmentsFields.isQueryInProgress

fun MainViewModel.getProductIsQueryExhausted():Boolean =
    getCurrentViewStateOrNew().productsFragmentsFields.isQueryExhausted


/////////////////////// catalog  var //////////////////

fun MainViewModel.getCatalogPageNumber():Int =
    getCurrentViewStateOrNew().catalogFragmentsFields.pageNumber

fun MainViewModel.getCatalogIsQueryInProgress():Boolean =
    getCurrentViewStateOrNew().catalogFragmentsFields.isQueryInProgress

fun MainViewModel.getCatalogIsQueryExhausted():Boolean =
    getCurrentViewStateOrNew().catalogFragmentsFields.isQueryExhausted
