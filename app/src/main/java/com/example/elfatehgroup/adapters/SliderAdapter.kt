package com.example.elfatehgroup.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.elfatehgroup.R
import com.example.elfatehgroup.models.SliderItem
import kotlinx.android.synthetic.main.layout_slider_item.view.*
import javax.inject.Inject

class SliderAdapter constructor(
    private val dataSource: List<SliderItem>
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.layout_slider_item, container, false)
        val sliderImageView = view.slider_image_view
        sliderImageView.setImageResource(dataSource.get(position).imageId)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

    override fun getCount() = dataSource.size
}