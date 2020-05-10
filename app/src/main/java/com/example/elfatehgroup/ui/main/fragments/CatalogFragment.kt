package com.example.elfatehgroup.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.elfatehgroup.R


class CatalogFragment : BaseMainFragment() {
    override val TAG: String
        get() = "CatalogFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalog, container, false)
    }



    override fun subscribeObservers() {
       // TODO("Not yet implemented")
    }
}
