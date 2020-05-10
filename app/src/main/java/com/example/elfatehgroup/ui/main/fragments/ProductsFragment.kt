package com.example.elfatehgroup.ui.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.elfatehgroup.R
import com.example.elfatehgroup.ui.main.viewmodel.setProductsList
import kotlinx.android.synthetic.main.fragment_products.*


class ProductsFragment : BaseMainFragment() {

    override val TAG: String
        get() = "ProductsFragment"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        testEventBtn.setOnClickListener {
            viewModel.fireEvent()
        }

    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            dataStateChangeListener.onDataStateChanged(dataState)
            dataState.data?.let { event ->
                event.getContentIfNotHandled()?.let { mainViewState ->
                    Log.e(TAG ,"subscribeObservers:  dataState: new data coming ...")
                    val list = mainViewState.productsFragmentsFields.productList
                    viewModel.setProductsList(list)
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer {  mainViewState ->
            mainViewState.productsFragmentsFields.let {productsFragmentsFields ->
                Log.e(TAG ,"subscribeObservers:  viewState: set to wedgiest...")
                productsFragmentsFields.productList.size
                Log.e(TAG ,"subscribeObservers:  viewState: size of products list   ${productsFragmentsFields.productList.size}")

            }

        })
    }
}
