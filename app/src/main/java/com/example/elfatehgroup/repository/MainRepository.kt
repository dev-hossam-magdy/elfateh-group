package com.example.elfatehgroup.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.room.Query
import com.example.blogapplication.repository.ApiResponseHandler
import com.example.blogapplication.repository.JobManager
import com.example.blogapplication.util.AbsentLiveData
import com.example.blogapplication.util.GenericApiResponse
import com.example.elfatehgroup.api.main.ElfatehGroupApi
import com.example.elfatehgroup.api.main.responses.CatalogItem
import com.example.elfatehgroup.api.main.responses.CatalogResponse
import com.example.elfatehgroup.api.main.responses.Product
import com.example.elfatehgroup.api.main.responses.ProductResponse
import com.example.elfatehgroup.base.BaseApplication
import com.example.elfatehgroup.di.annotations.MainScope
import com.example.elfatehgroup.persistance.dao.CatalogItemDao
import com.example.elfatehgroup.persistance.dao.ProductDao
import com.example.elfatehgroup.ui.main.state.MainViewState
import com.example.elfatehgroup.util.Constants
import com.example.elfatehgroup.util.DataState
import com.example.elfatehgroup.util.Response
import com.example.elfatehgroup.util.ResponseType
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import javax.inject.Inject


@MainScope
class MainRepository
@Inject constructor(
    private val application: BaseApplication,
    private val elfatehGroupApi: ElfatehGroupApi,
    private val productDao: ProductDao,
    private val catalogItemDao: CatalogItemDao
) : JobManager("MainRepository") {

    private val TAG = "MainRepository"

    fun fetchingProductsList(
        pageNumber: Int
    ): LiveData<DataState<MainViewState>> =
        object : ApiResponseHandler<ProductResponse, List<Product>, MainViewState>(
            isNetworkAvailable = application.isConnectedToTheInternet(),
            shouldLoadCachedData = true,
            shouldCanceledIfNoNetworkConnection = false,
            isNetworkRequest = true
        ) {
            override suspend fun handelApiSuccessResponse(response: GenericApiResponse.ApiSuccessResponse<ProductResponse>) {
                Log.e(TAG, "handelApiSuccessResponse")
                response.body.data.let { list ->
                    Log.e(TAG, "handelApiSuccessResponse: data not null ${list.size}")
                    updatedLocalDataBase(list)
                    makeCachedRequestAndReturn()
                }
            }

            override suspend fun makeCachedRequestAndReturn() {
                withContext(Main) {
                    Log.e(TAG, "makeCachedRequestAndReturn:")
                    result.addSource(loadCachedData()) { mainViewState ->
                        mainViewState.productsFragmentsFields.isQueryInProgress = false
                        if (mainViewState.productsFragmentsFields.productList.size <
                            (pageNumber * Constants.DEFULT_PRODUCT_PAGE_SIZE)
                        )
                            mainViewState.productsFragmentsFields.isQueryExhausted = true
                        Log.e(
                            TAG, """makeCachedRequestAndReturn: page number $pageNumber ,
                            | isQueryInProgress:${mainViewState.productsFragmentsFields.isQueryInProgress} ,
                            | isQueryExhausted: ${mainViewState.productsFragmentsFields.isQueryExhausted}""".trimMargin()
                        )

                        onCompleteJob(
                            DataState.Data(
                                data = mainViewState,
                                response = Response(
                                    message = "data loaded",
                                    responseType = ResponseType.None()
                                )
                            )
                        )
                        Log.e(TAG, "makeCachedRequestAndReturn: onCompleteJob:")

                    }
                }
            }

            override fun loadCachedData(): LiveData<MainViewState> =
                productDao.getProductsList(pageNumber).switchMap { list ->
                    return@switchMap liveData<MainViewState> {
                        emit(
                            value = MainViewState(
                                MainViewState.ProductsFragmentsFields(
                                    productList = list,
                                    isQueryInProgress = true
                                )
                            )
                        )
                        Log.e(TAG, "loadCachedData: ")
                    }
                }

            override suspend fun updatedLocalDataBase(cachedObject: List<Product>?) {
                cachedObject?.let {
                    Log.e(TAG, "updatedLocalDataBase: ")
                    withContext(IO) {
                        productDao.insertProduct(it)
                        Log.e(TAG, "updatedLocalDataBase: withContext:")
                    }

                }

            }

            override fun createCall(): LiveData<GenericApiResponse<ProductResponse>> =
                elfatehGroupApi.getProductsList(pageNumber)

            override fun setJob(job: Job) {
                addJob("fetchingProductsList", job)
            }
        }.getResultAsLiveData()


    fun filterProducts(
        query: String,
        pageNumber: Int,
        isQueryExhausted: Boolean
    ): LiveData<DataState<MainViewState>> =
        object : ApiResponseHandler<Void, Any, MainViewState>(
            isNetworkAvailable = application.isConnectedToTheInternet(),
            shouldLoadCachedData = true,
            shouldCanceledIfNoNetworkConnection = false,
            isNetworkRequest = false
        ) {
            // not needed in this case
            override suspend fun handelApiSuccessResponse(response: GenericApiResponse.ApiSuccessResponse<Void>) {

            }

            override suspend fun makeCachedRequestAndReturn() {
                withContext(Main) {
                    val viewState = loadCachedData()
                    result.addSource(viewState) { mainViewState ->
                        onCompleteJob(
                            dataState = DataState.Data(
                                data = mainViewState,
                                response = null
                            )
                        )

                    }
                }
            }

            override fun loadCachedData(): LiveData<MainViewState> =
                productDao.filterProducts(query = query).switchMap { list ->
                    return@switchMap liveData<MainViewState> {
                        emit(
                            value = MainViewState(
                                productsFragmentsFields = MainViewState.ProductsFragmentsFields(
                                    productList = list,
                                    isQueryExhausted = isQueryExhausted,
                                    pageNumber = pageNumber,
                                    isQueryInProgress = false
                                )
                            )
                        )
                    }
                }

            // not needed in this case
            override suspend fun updatedLocalDataBase(cachedObject: Any?) {

            }

            override fun createCall(): LiveData<GenericApiResponse<Void>> =
                AbsentLiveData.create()

            override fun setJob(job: Job) {
                addJob("filterProducts", job)
            }
        }.getResultAsLiveData()

    fun getCatalogList(
        pageNumber: Int,
        isQueryExhausted: Boolean
    ): LiveData<DataState<MainViewState>> =
        object : ApiResponseHandler<CatalogResponse, List<CatalogItem>, MainViewState>
            (
            isNetworkRequest = true,
            shouldCanceledIfNoNetworkConnection = false,
            shouldLoadCachedData = true,
            isNetworkAvailable = application.isConnectedToTheInternet()
        ) {
            override suspend fun handelApiSuccessResponse(response: GenericApiResponse.ApiSuccessResponse<CatalogResponse>) {
                response.body.data.let { list ->
                    updatedLocalDataBase(list)
                    makeCachedRequestAndReturn()
                }

            }

            override suspend fun makeCachedRequestAndReturn() {
                withContext (Main){
                    result.addSource(loadCachedData()){ viewState ->
                        viewState.catalogFragmentsFields.isQueryInProgress = false
                        onCompleteJob(
                            dataState = DataState.Data(
                                data = viewState,
                                response = null
                            )
                        )

                    }
                }

            }

            override fun loadCachedData(): LiveData<MainViewState> =
                catalogItemDao.selectListOfCatalogItems(pageNumber).switchMap { list ->
                    return@switchMap liveData <MainViewState>{
                        emit(value = MainViewState(
                            catalogFragmentsFields = MainViewState.CatalogFragmentsFields(
                                catalogItemList = list,
                                pageNumber = pageNumber,
                                isQueryExhausted = isQueryExhausted,
                                isQueryInProgress = false
                            )
                        ))
                    }

                }

            override suspend fun updatedLocalDataBase(cachedObject: List<CatalogItem>?) {
                cachedObject?.let { list ->
                    catalogItemDao.insertListOfCatalogItems(list)
                }
            }

            override fun createCall(): LiveData<GenericApiResponse<CatalogResponse>> =
                elfatehGroupApi.getCatalogItemsList(pageNumber)

            override fun setJob(job: Job) {
                addJob("getCatalogList",job)
            }
        }.getResultAsLiveData()

}