package com.example.elfatehgroup.ui.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.elfatehgroup.R
import com.example.elfatehgroup.adapters.CatalogItemAdapter
import com.example.elfatehgroup.ui.main.state.MainStateEvent
import com.example.elfatehgroup.ui.main.viewmodel.loadNextPage
import com.example.elfatehgroup.ui.main.viewmodel.setCatalogsList
import com.example.elfatehgroup.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_catalog.*
import javax.inject.Inject


class CatalogFragment : BaseMainFragment() {
    override val TAG: String
        get() = "CatalogFragment"

    @Inject
    lateinit var catalogItemAdapter: CatalogItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolBar(getString(R.string.catalog_fragment_title))
        viewModel.setStateEvent(MainStateEvent.GetCatalogListEvent())

        initRecyclerView()

        linerBtn.setOnClickListener {
            catalogRecyclerView.layoutManager = LinearLayoutManager(this@CatalogFragment.context)
        }
        grideBtn.setOnClickListener {
            catalogRecyclerView.layoutManager = GridLayoutManager(this@CatalogFragment.context,2)

        }
    }

    private fun initRecyclerView() {
        catalogRecyclerView.apply {
            adapter = catalogItemAdapter
            layoutManager = GridLayoutManager(this@CatalogFragment.context,2)

            val itemDecoration = TopSpacingItemDecoration(30)
            removeItemDecoration(itemDecoration)
            addItemDecoration(itemDecoration)

//            addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                    super.onScrollStateChanged(recyclerView, newState)
//                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
//                    val lastPassion = layoutManager.findLastVisibleItemPosition()
//                    if (lastPassion == recyclerView.adapter?.itemCount?.minus(1)) {
//                        Log.e(TAG, "addOnScrollListener: load the next page")
//
//                    }
//                }
//            })
        }
    }


    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            dataState?.let {
                dataStateChangeListener.onDataStateChanged(it)
                it.data?.let { event ->
                    event.getContentIfNotHandled()?.let { mainViewState ->
                        viewModel.setCatalogsList(mainViewState.catalogFragmentsFields.catalogItemList)
                    }

                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { mainViewState ->
            mainViewState.catalogFragmentsFields.let {catalogFragmentsFields ->
                catalogFragmentsFields.catalogItemList.let { list ->
                    Log.e(TAG,"viewState: catalogFragmentsFields: catalogItemList: new list is coming ${list.size}")
                    catalogItemAdapter.submitList(list)
                }
            }
        })
    }
}
