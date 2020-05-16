package com.example.elfatehgroup.ui.main.viewmodel

fun MainViewModel.getProductPageNumber():Int =
    getCurrentViewStateOrNew().productsFragmentsFields.pageNumber

fun MainViewModel.getSearchQuery():String =
    getCurrentViewStateOrNew().productsFragmentsFields.searchQuery

fun MainViewModel.getIsQueryInProgress():Boolean =
    getCurrentViewStateOrNew().productsFragmentsFields.isQueryInProgress

fun MainViewModel.getProductIsQueryExhausted():Boolean =
    getCurrentViewStateOrNew().productsFragmentsFields.isQueryExhausted