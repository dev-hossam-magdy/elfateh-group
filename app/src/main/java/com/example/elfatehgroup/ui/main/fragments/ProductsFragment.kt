package com.example.elfatehgroup.ui.main.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elfatehgroup.R
import com.example.elfatehgroup.adapters.ProductAdapter
import com.example.elfatehgroup.ui.main.state.MainStateEvent
import com.example.elfatehgroup.ui.main.state.MainViewState
import com.example.elfatehgroup.ui.main.viewmodel.*
import com.example.elfatehgroup.util.Constants
import com.example.elfatehgroup.util.DataState
import com.example.elfatehgroup.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_products.*
import javax.inject.Inject


class ProductsFragment : BaseMainFragment() {

    override val TAG: String
        get() = "ProductsFragment"

    @Inject
    lateinit var productAdapter: ProductAdapter
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        if (savedInstanceState == null)
            viewModel.loadFirstPage()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        productRecyclerView.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(this@ProductsFragment.context)

            val itemDecoration = TopSpacingItemDecoration(30)
            removeItemDecoration(itemDecoration)
            addItemDecoration(itemDecoration)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPassion = layoutManager.findLastVisibleItemPosition()
                    if (lastPassion == recyclerView.adapter?.itemCount?.minus(1)) {
                        Log.e(TAG, "addOnScrollListener: load the next page")
                        viewModel.loadNextPage()
                    }
                }
            })
        }
    }

    override fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            handelPagination(dataState)
            dataStateChangeListener.onDataStateChanged(dataState)
            Log.e(TAG, "subscribeObservers: onDataStateChanged.isLoading ${dataState.isLoading}")
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { mainViewState ->
            mainViewState.productsFragmentsFields.let { productsFragmentsFields ->
                Log.e(TAG, "subscribeObservers:  viewState: set to wedgiest...")
                productAdapter.submitList(productsFragmentsFields.productList)
                Log.e(
                    TAG,
                    "subscribeObservers:  viewState: size of products list   ${productsFragmentsFields.productList.size}"
                )


            }

        })
    }

    private fun handelPagination(dataState: DataState<MainViewState>?) {
        dataState?.let {
            when (it) {
                is DataState.Data -> handelPaginationDataState(it)
                is DataState.Error -> handelPaginationErrorState(it)
            }

        }

    }

    private fun handelPaginationDataState(dataState: DataState.Data<MainViewState>) {
        dataState.data?.let { event ->
            event.getContentIfNotHandled()?.let { mainViewState ->
                viewModel.handelIncomingProductsListData(mainViewState)
            }
        }
    }

    private fun handelPaginationErrorState(dataState: DataState.Error<MainViewState>) {
        dataState.response?.let { event ->
            // to prevent handling data
            event.peekContent().message?.let {
                if (it.equals(Constants.SERVER_PAGINATION_ERROR)) {
                    event.getContentIfNotHandled()
                    viewModel.setIsQueryExhausted(true)
                }

            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu,menu)
        setupSearchView(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
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

                viewModel.loadCurrentPage()
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

                onProductsSearch(searchQuery)
            }
            return@setOnEditorActionListener true
        }

        // handel button event
        (searchView.findViewById(R.id.search_go_btn) as View).setOnClickListener {
            val searchQuery  = searchPlate.text.toString()
            Log.e(TAG, "setOnClickListener (button event) :  Getting the search value $searchQuery")

            onProductsSearch(searchQuery)
        }


    }

    private fun onProductsSearch(searchQuery: String) {
        viewModel.setStateEvent(MainStateEvent.FilterProductsEvent(searchQuery))
        dataStateChangeListener.hideKeyboard()
    }
}
