package com.example.elfatehgroup.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.blogapplication.util.AbsentLiveData
import com.example.elfatehgroup.repository.MainRepository
import com.example.elfatehgroup.ui.BaseViewModel
import com.example.elfatehgroup.ui.main.state.MainStateEvent
import com.example.elfatehgroup.ui.main.state.MainViewState
import com.example.elfatehgroup.util.DataState
import javax.inject.Inject

class MainViewModel
@Inject
constructor(
    private val mainRepository: MainRepository
) : BaseViewModel<MainStateEvent, MainViewState>() {

    private val TAG = "MainViewModel"

    override fun initNewState() = MainViewState()

    override fun handelStatEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> =
        when (stateEvent) {
            is MainStateEvent.GetProductsListEvent -> {
                Log.e(TAG, "GetProductsListEvent")
                mainRepository.fetchingProductsList(pageNumber = getProductPageNumber())
            }
            is MainStateEvent.FilterProductsEvent -> {
                mainRepository.filterProducts(
                    query = stateEvent.query,
                    pageNumber = getProductPageNumber(),
                    isQueryExhausted = getProductIsQueryExhausted()
                )
            }
            is MainStateEvent.GetCatalogListEvent -> {
                mainRepository.getCatalogList(pageNumber = getCatalogPageNumber())
            }
            is MainStateEvent.FilterCatalogEvent -> {
                mainRepository.filterCatalogList(
                    query = stateEvent.query,
                    pageNumber = getCatalogPageNumber(),
                    isQueryExhausted = getCatalogIsQueryExhausted()
                )
            }
            is MainStateEvent.None -> {
                liveData<DataState<MainViewState>> {
                    emit(value = DataState.Data(null, null))
                }
            }

        }


    override fun onCleared() {
        super.onCleared()
        mainRepository.cancelActiveJobs()
        handelPendingData()
    }

    override fun handelPendingData() {
        setStateEvent(MainStateEvent.None())
    }
}