package com.example.elfatehgroup.ui.main

import android.os.Bundle
import android.widget.ProgressBar

import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.elfatehgroup.R
import com.example.elfatehgroup.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    override val progressBar: ProgressBar
        get() = loadingProgressBar
    override val TAG: String
        get() = "MainActivity"
//    @Inject
//    lateinit var str:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation() {
        NavigationUI.setupWithNavController(bottom_nav_view,findNavController(R.id.nav_host_fragment))
    }

    override fun getRecurseId()=R.layout.activity_main
}
