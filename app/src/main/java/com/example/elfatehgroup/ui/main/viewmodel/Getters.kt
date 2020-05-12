package com.example.elfatehgroup.ui.main.viewmodel

fun MainViewModel.getPageNumber():Int =
    getCurrentViewStateOrNew().productsFragmentsFields.pageNumber

fun MainViewModel.getSearchQuery():String =
    getCurrentViewStateOrNew().productsFragmentsFields.searchQuery

fun MainViewModel.getIsQueryInProgress():Boolean =
    getCurrentViewStateOrNew().productsFragmentsFields.isQueryInProgress

fun MainViewModel.getIsQueryExhausted():Boolean =
    getCurrentViewStateOrNew().productsFragmentsFields.isQueryExhausted