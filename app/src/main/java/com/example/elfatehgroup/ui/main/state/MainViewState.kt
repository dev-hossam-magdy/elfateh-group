package com.example.elfatehgroup.ui.main.state

import com.example.elfatehgroup.api.main.responses.Product

data class MainViewState(
    // this is fields of product fragment
    var productsFragmentsFields: ProductsFragmentsFields =ProductsFragmentsFields()
){

    data class ProductsFragmentsFields(
        var productList:List<Product> =ArrayList(),
        var pageNumber:Int = 1
    )
}