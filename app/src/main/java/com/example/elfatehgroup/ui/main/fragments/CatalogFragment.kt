package com.example.elfatehgroup.ui.main.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.elfatehgroup.R
import com.example.elfatehgroup.adapters.CatalogItemAdapter
import com.example.elfatehgroup.ui.main.state.MainStateEvent
import com.example.elfatehgroup.ui.main.state.MainViewState
import com.example.elfatehgroup.ui.main.viewmodel.*
import com.example.elfatehgroup.util.CatalogItemDecoration
import com.example.elfatehgroup.util.Constants
import com.example.elfatehgroup.util.DataState
import com.example.elfatehgroup.util.ErrorHandling
import kotlinx.android.synthetic.main.fragment_catalog.*
import javax.inject.Inject


class CatalogFragment : BaseMainFragment() {
    override val TAG: String
        get() = "CatalogFragment"
    private lateinit var searchView: SearchView

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
        setHasOptionsMenu(true)
        setupToolBar(getString(R.string.catalog_fragment_title))
        viewModel.setStateEvent(MainStateEvent.GetCatalogListEvent())

        if (savedInstanceState == null)
            viewModel.loadCatalogFirstPage()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        catalogRecyclerView.apply {
            adapter = catalogItemAdapter
            layoutManager = GridLayoutManager(this@CatalogFragment.context, 2)

            val itemDecoration = CatalogItemDecoration(30)
            removeItemDecoration(itemDecoration)
            addItemDecoration(itemDecoration)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    val lastPassion = layoutManager.findLastVisibleItemPosition()
                    if (lastPassion == recyclerView.adapter?.itemCount?.minus(1)) {
                        Log.e(TAG, "addOnScrollListener: load the next page")
                        viewModel.loadCatalogNextPage()

                    }
                }
            })
        }
    }


    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            dataState?.let {
                handelCatalogPagination(it)
                dataStateChangeListener.onDataStateChanged(it)
                it.data?.let { event ->
                    event.getContentIfNotHandled()?.let { mainViewState ->
                        viewModel.setCatalogsList(mainViewState.catalogFragmentsFields.catalogItemList)
                    }

                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { mainViewState ->
            mainViewState.catalogFragmentsFields.let { catalogFragmentsFields ->
                catalogFragmentsFields.catalogItemList.let { list ->
                    Log.e(
                        TAG,
                        "viewState: catalogFragmentsFields: catalogItemList: new list is coming ${list.size}"
                    )
                    catalogItemAdapter.submitList(list)
                }
            }
        })
    }

    private fun handelCatalogPagination(dataState: DataState<MainViewState>) {
        dataState.let {
            when (it) {
                is DataState.Error -> handelPaginationError(it)
                is DataState.Data -> handelPaginationData(it)

            }

        }
    }

    private fun handelPaginationError(dataSate: DataState.Error<MainViewState>) {
        dataSate.response?.let { event ->
            event.peekContent().let { response ->
                if (response.message.equals(Constants.SERVER_PAGINATION_ERROR)) {
                    event.getContentIfNotHandled()
                    viewModel.setCatalogIsQueryExhausted(true)
                }
            }
        }
    }

    private fun handelPaginationData(dataSate: DataState.Data<MainViewState>) {
        dataSate.let {
            it.data?.let { event ->
                event.getContentIfNotHandled()?.let { mainViewState ->
                    viewModel.handelIncomingCatalogListData(mainViewState)

                }

            }
        }

    }


    private fun setupSearchView(menu: Menu) {
        activity?.run {
            val searchManger: SearchManager =
                getSystemService(Context.SEARCH_SERVICE) as SearchManager
            searchView = menu.findItem(R.id.action_search).actionView as SearchView
            searchView.setSearchableInfo(searchManger.getSearchableInfo(componentName))
            searchView.maxWidth = Int.MAX_VALUE
            searchView.setIconifiedByDefault(true)
            searchView.isSubmitButtonEnabled = true

            searchView.setOnCloseListener {
                Log.e(TAG,"setupSearchView: setOnCloseListener:")

                viewModel.loadCatalogCurrentPage()
                searchView.onActionViewCollapsed()
                dataStateChangeListener.hideKeyboard()
                true
            }
        }

        // handling button on keyboard

        val searchPlate = searchView.findViewById(R.id.search_src_text) as EditText
        searchPlate.setOnEditorActionListener { v, actionId, event ->

            if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED || actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchQuery  = v.text.toString()
                Log.e(TAG, "setOnEditorActionListener (keyboard event) :  Getting the search vlue $searchQuery")

                onCatalogSearch(searchQuery)
            }
            return@setOnEditorActionListener true
        }

        (searchView.findViewById(R.id.search_go_btn) as View).setOnClickListener {
            val searchQuery  = searchPlate.text.toString()
            Log.e(TAG, "setOnClickListener (button event) :  Getting the search value $searchQuery")

            onCatalogSearch(searchQuery)
        }


    }

    private fun onCatalogSearch(searchQuery: String) {
        viewModel.setStateEvent(MainStateEvent.FilterCatalogEvent(searchQuery))
        dataStateChangeListener.hideKeyboard()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu,menu)
        setupSearchView(menu)
    }
}
