package com.example.blogapplication.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.blogapplication.util.GenericApiResponse
import com.example.elfatehgroup.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

abstract class ApiResponseHandler<ResponseObject, CachedObject, ViewStatType>(
    private val isNetworkAvailable: Boolean,
    private val isNetworkRequest: Boolean,
    private val shouldCanceledIfNoNetworkConnection: Boolean,
    private val shouldLoadCachedData: Boolean
) {

    private val TAG = "ApiResponseHandler"

    protected val result = MediatorLiveData<DataState<ViewStatType>>()
    protected lateinit var job: CompletableJob
    protected lateinit var coroutineScope: CoroutineScope

    init {
        setJob(initJob())
        setResultValue(DataState.Loading(isLoading = true, cachedData = null))
        if (shouldLoadCachedData) {
            val cachedData = loadCachedData()
            result.addSource(cachedData) { viewStat ->
                result.removeSource(cachedData)
                setResultValue(DataState.Loading(isLoading = true, cachedData = viewStat))

            }
        }
        if (isNetworkRequest)
            if (isNetworkAvailable) {
                doNetworkRequest()
            } else
                if (shouldCanceledIfNoNetworkConnection)
                    onErrorReturn(ErrorHandling.UNABLE_TODO_OPERATION_WO_INTERNET, true, false)
                else

                    doCacheRequest()
        else
            doCacheRequest()
    }

    private fun doCacheRequest() {
        coroutineScope.launch {
            makeCachedRequestAndReturn()
        }
    }

    private fun doNetworkRequest() {
        Log.e(TAG ,"doNetworkRequest:")
        coroutineScope.launch {
            Log.e(TAG ,"doNetworkRequest: coroutineScope:")
            delay(Constants.TESTING_NETWORK_DELAY)
            Log.e(TAG ,"doNetworkRequest: coroutineScope: delay:")
            withContext(Main) {
                Log.e(TAG ,"doNetworkRequest: coroutineScope: withContext:")
                val apiResponse = createCall()
                result.addSource(apiResponse) { response ->
                    result.removeSource(apiResponse)
                    coroutineScope.launch {
                        handelNetWorkCall(response)
                    }
                }
            }

        }
        GlobalScope.launch(IO) {
            delay(Constants.NETWORK_TIMEOUT)
            if (!job.isCompleted) {
                job.cancel(CancellationException(ErrorHandling.UNABLE_TO_RESOLVE_HOST))
            }
        }
    }

    private suspend fun handelNetWorkCall(response: GenericApiResponse<ResponseObject>?) {
        when (response) {
            is GenericApiResponse.ApiSuccessResponse -> {
                Log.e(TAG, "handelNetWorkCall: and ApiSuccessResponse")
                handelApiSuccessResponse(response)
            }
            is GenericApiResponse.ApiEmptyResponse -> {
                Log.e(TAG, "handelNetWorkCall: and ApiSuccessResponse")
                onErrorReturn("HTTP-204 empty response", true, false)

            }
            is GenericApiResponse.ApiErrorResponse -> {
                Log.e(TAG, "handelNetWorkCall: and ApiErrorResponse")
                onErrorReturn(response.errorMessage, true, false)

            }
        }
    }

    @OptIn(InternalCoroutinesApi::class)
    private fun initJob(): Job {
        job = Job()
        job.invokeOnCompletion(onCancelling = true, invokeImmediately = true, handler =
        object : CompletionHandler {
            override fun invoke(cause: Throwable?) {
                if (job.isCancelled) {
                    Log.e(TAG, "initJob: invokeOnCompletion: job is Cancelled ${cause?.message}")
                    cause?.let {
                        onErrorReturn(it.message, false, true)
                    } ?: onErrorReturn(null, false, true)
                } else if (job.isCompleted) {
                    Log.e(TAG, "initJob: invokeOnCompletion: job is Completed")
                }
            }
        })
        coroutineScope = CoroutineScope(Dispatchers.IO + job)
        return job
    }

    fun onErrorReturn(errorMessage: String?, shouldUseDialog: Boolean, shouldUseToast: Boolean) {
        var msg = errorMessage
        var responseType: ResponseType = ResponseType.None()
        var useDialog = shouldUseDialog
        if (msg == null)
            msg = Constants.ERROR_UNKNOWN
        else if (ErrorHandling.isNetworkError(msg)) {
            msg = ErrorHandling.ERROR_CHECK_NETWORK_CONNECTION
            useDialog = false
        }
        if (useDialog)
            responseType = ResponseType.Dialog()
        else if (shouldUseToast)
            responseType = ResponseType.Toast()

        onCompleteJob(
            DataState.Error(
                response = Response(
                    message = msg,
                    responseType = responseType
                )
            )
        )

    }

    fun onCompleteJob(dataState: DataState<ViewStatType>) {
        GlobalScope.launch(Main) {
            job.complete()
            setResultValue(dataState)
        }
    }

    private fun setResultValue(dataState: DataState<ViewStatType>) {
        result.value = dataState
    }

    fun getResultAsLiveData() = result as LiveData<DataState<ViewStatType>>

    // fun that handel api response if it success
    abstract suspend fun handelApiSuccessResponse(response: GenericApiResponse.ApiSuccessResponse<ResponseObject>)

    // single way of truth always load data from database
    abstract suspend fun makeCachedRequestAndReturn()
    // load data from cache
    abstract fun loadCachedData(): LiveData<ViewStatType>
    // update local data base
    abstract suspend fun updatedLocalDataBase(cachedObject: CachedObject?)

    // create api call response
    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>
    // set jobs to cancel it
    abstract fun setJob(job: Job)

}