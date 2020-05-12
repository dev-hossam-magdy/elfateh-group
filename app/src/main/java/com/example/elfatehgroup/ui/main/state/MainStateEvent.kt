package com.example.elfatehgroup.ui.main.state

import androidx.room.Query

sealed class MainStateEvent {

    class GetProductsListEvent:MainStateEvent()

    data class FilterProductsEvent(val query: String):MainStateEvent()

    class None:MainStateEvent()
}