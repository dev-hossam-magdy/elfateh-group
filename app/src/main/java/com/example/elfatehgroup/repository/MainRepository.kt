package com.example.elfatehgroup.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.blogapplication.repository.ApiResponseHandler
import com.example.blogapplication.repository.JobManager
import com.example.blogapplication.util.GenericApiResponse
import com.example.elfatehgroup.api.main.ElfatehGroupApi
import com.example.elfatehgroup.api.main.responses.Product
import com.example.elfatehgroup.api.main.responses.ProductResponse
import com.example.elfatehgroup.di.annotations.MainScope
import com.example.elfatehgroup.persistance.dao.ProductDao
import com.example.elfatehgroup.ui.main.state.MainViewState
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
//    private val application: BaseApplication,
    private val elfatehGroupApi: ElfatehGroupApi,
    private val productDao: ProductDao
) : JobManager("MainRepository") {

    private val TAG = "MainRepository"

    fun fetchingProductsList(
        pageNumber: Int
    ): LiveData<DataState<MainViewState>> =
        object : ApiResponseHandler<ProductResponse, List<Product>, MainViewState>(
            isNetworkAvailable = true,
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

                    result.addSource(loadCachedData()) { MainViewState ->
                        onCompleteJob(
                            DataState.Data(
                                data = MainViewState,
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
                                    productList = list
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




}