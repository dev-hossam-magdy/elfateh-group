package com.example.elfatehgroup.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.example.elfatehgroup.R
import com.example.elfatehgroup.adapters.SliderAdapter
import com.example.elfatehgroup.models.SliderItem
import kotlinx.android.synthetic.main.fragment_home.*
import me.relex.circleindicator.CircleIndicator
import javax.inject.Inject


class HomeFragment : BaseMainFragment() {

    override val TAG: String
        get() = "HomeFragment"


    @Inject
    lateinit var requestManager: RequestManager
    lateinit var adapter: SliderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolBar(getString(R.string.home_fragment_title))
        initViewPager()

    }

    private fun initViewPager() {
        adapter = SliderAdapter(getDummyList())
        sliderViewPager.adapter = adapter
        indicator.setViewPager(sliderViewPager)
    }

    override fun subscribeObservers() {

    }

    private fun getDummyList(): List<SliderItem> =
        mutableListOf(
            SliderItem(R.drawable.slider_1),
            SliderItem(R.drawable.slider_2),
            SliderItem(R.drawable.slider_3)
        )


}
