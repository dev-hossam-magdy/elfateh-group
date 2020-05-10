package com.example.elfatehgroup.ui.main.state

sealed class MainStateEvent {

    class GetProductsListEvent:MainStateEvent()

    class None:MainStateEvent()
}