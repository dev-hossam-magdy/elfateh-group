package com.example.elfatehgroup.ui.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.elfatehgroup.ViewModelProviderFactory
import com.example.elfatehgroup.ui.DataStateChangeListener
import com.example.elfatehgroup.ui.main.viewmodel.MainViewModel
import dagger.android.support.DaggerFragment
import java.lang.Exception
import javax.inject.Inject

abstract class BaseMainFragment:DaggerFragment() {

    abstract val TAG :String
    @Inject
    lateinit var factory: ViewModelProviderFactory
    lateinit var dataStateChangeListener: DataStateChangeListener

    protected lateinit var viewModel: MainViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            dataStateChangeListener = context as DataStateChangeListener

        }catch(e: ClassCastException){
            Log.e(TAG, "$context must implement DataStateChangeListener" )
        }
        viewModel = activity?.run {
            ViewModelProvider(this,factory).get(MainViewModel::class.java)

        }?:throw Exception("activity not craeted ")

        subscribeObservers()
    }

    abstract fun subscribeObservers()
}