package com.example.elfatehgroup.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.elfatehgroup.R
import com.example.elfatehgroup.base.BaseActivity
import com.example.elfatehgroup.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val TAG: String
        get() = "MainActivity"
    @Inject
    lateinit var str:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.str = str
        setupNavigation()
    }

    private fun setupNavigation() {
        NavigationUI.setupWithNavController(binding.bottomNavView,findNavController(R.id.nav_host_fragment))
    }

    override fun getRecurseId()=R.layout.activity_main
}
