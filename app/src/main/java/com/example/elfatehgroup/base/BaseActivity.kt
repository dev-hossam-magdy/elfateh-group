package com.example.elfatehgroup.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import com.example.elfatehgroup.R
import com.example.elfatehgroup.ui.DataStateChangeListener
import com.example.elfatehgroup.ui.displayDialogMessage
import com.example.elfatehgroup.ui.displayToastMessage
import com.example.elfatehgroup.ui.showOrHideProgressBar
import com.example.elfatehgroup.util.*
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class BaseActivity : DaggerAppCompatActivity(), DataStateChangeListener {
    abstract protected val TAG: String
    abstract protected val progressBar:ProgressBar

//    protected lateinit var binding: BindingClass
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this , getRecurseId())
    }

    override fun onDataStateChanged(dataState: DataState<*>) {
        GlobalScope.launch(Main) {
            Log.e(TAG ,"onDataStateChanged: isLoading: ${dataState.isLoading}")
            progressBar.showOrHideProgressBar(dataState.isLoading)
            when(dataState){
                is DataState.Error -> {
                    handelState(dataState.response, R.string.error)
                }
                is DataState.Data ->{
                    handelState(dataState.response, R.string.info)

                }
            }
        }

    }

    private fun handelState(response: Event<Response>?, resourceId: Int) {
        response?.let { event ->
            event.getContentIfNotHandled()?.let {res ->
                Log.e(TAG,"handelStateError: there is an error ${res.message}")
                val msg = res.message?.let {
                    it
                }?:Constants.ERROR_UNKNOWN
                when(res.responseType){
                    is ResponseType.Dialog -> this.displayDialogMessage(resourceId,msg)

                    is ResponseType.Toast -> this.displayToastMessage(msg)

                    is ResponseType.None -> Log.e(TAG ,"handelStateError: ResponseType.None: $msg")
                }

            }

        }
    }

    override fun hideKeyboard() {
        currentFocus?.let {
            val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    abstract fun getRecurseId(): Int
}