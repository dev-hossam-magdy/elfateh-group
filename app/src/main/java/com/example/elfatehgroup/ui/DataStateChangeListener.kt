package com.example.elfatehgroup.ui

import com.example.elfatehgroup.util.DataState

interface DataStateChangeListener {
    fun onDataStateChanged(dataState:DataState<*>)
}