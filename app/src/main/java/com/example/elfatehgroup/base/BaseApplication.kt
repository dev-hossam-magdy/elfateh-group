package com.example.elfatehgroup.base

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.example.elfatehgroup.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import java.lang.Exception

class BaseApplication : DaggerApplication() {
    private val TAG = "BaseApplication"
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    fun isConnectedToTheInternet(): Boolean =
        try {
            val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            cm.activeNetworkInfo.isConnected
        } catch (e: Exception) {
            Log.e(TAG, "isConnectedToTheInternet: ${e.message}")
            false
        }
}