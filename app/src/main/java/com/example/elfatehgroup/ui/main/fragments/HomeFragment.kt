package com.example.elfatehgroup.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager

import com.example.elfatehgroup.R
import javax.inject.Inject

class HomeFragment : BaseMainFragment() {

    override val TAG: String
        get() = "HomeFragment"

    @Inject
    lateinit var requestManager: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun subscribeObservers() {
       // TODO("Not yet implemented")
    }

}
