package com.example.elfatehgroup.ui.main.state

import androidx.room.Query

sealed class MainStateEvent {

    class GetProductsListEvent:MainStateEvent()

    class GetCatalogListEvent:MainStateEvent()

    data class FilterProductsEvent(val query: String):MainStateEvent()

    data class FilterCatalogEvent(val query: String):MainStateEvent()

    class None:MainStateEvent()
}