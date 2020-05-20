package com.example.elfatehgroup.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.elfatehgroup.R
import com.example.elfatehgroup.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {


    override val TAG: String
        get() = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()

    }

    private fun setupToolbarWithNavController() {
//        NavigationUI.setupActionBarWithNavController(this,findNavController(R.id.nav_host_fragment))

        setSupportActionBar(app_toolbar)

    }

    private fun setupNavigation() {
        NavigationUI.setupWithNavController(
            bottom_nav_view,
            findNavController(R.id.nav_host_fragment)
        )
        setupToolbarWithNavController()
    }

    override fun getRecurseId() = R.layout.activity_main

    override fun showOrHideProgressBar(isLoading: Boolean) {
        loadingProgressBar.visibility =
            if (isLoading)
                View.VISIBLE
            else
                View.GONE
    }
}
