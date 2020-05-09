package com.example.elfatehgroup.base

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity<BindingClass : ViewDataBinding> : DaggerAppCompatActivity() {
    abstract protected val TAG: String

    protected lateinit var binding: BindingClass
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , getRecurseId())
    }

    abstract fun getRecurseId(): Int
}