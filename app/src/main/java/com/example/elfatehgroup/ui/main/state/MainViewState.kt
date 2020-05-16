package com.example.elfatehgroup.ui.main.state

import android.app.DownloadManager
import com.example.elfatehgroup.api.main.responses.CatalogItem
import com.example.elfatehgroup.api.main.responses.Product

data class MainViewState(
    // this is fields of product fragment
    var productsFragmentsFields: ProductsFragmentsFields = ProductsFragmentsFields(),

    // this is fields of product fragment
    var catalogFragmentsFields: CatalogFragmentsFields = CatalogFragmentsFields()
) {

    data class ProductsFragmentsFields(
        var productList: List<Product> = ArrayList(),
        override var pageNumber: Int = 1,
        override var searchQuery: String = "",
        override var isQueryInProgress: Boolean = false,
        override var isQueryExhausted: Boolean = false
    ):PaginationAttributes()

    data class CatalogFragmentsFields(
        var catalogItemList: List<CatalogItem> = ArrayList(),
        override var pageNumber: Int = 1,
        override var searchQuery: String = "",
        override var isQueryInProgress: Boolean = false,
        override var isQueryExhausted: Boolean = false
    ) : PaginationAttributes()

    abstract class PaginationAttributes(
        open var pageNumber: Int = 1,
        open var searchQuery: String = "",
        open var isQueryInProgress: Boolean = false,
        open var isQueryExhausted: Boolean = false
    )
}
