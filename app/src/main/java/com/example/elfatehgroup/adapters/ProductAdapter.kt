package com.example.elfatehgroup.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.elfatehgroup.R
import com.example.elfatehgroup.api.main.responses.Product
import com.example.elfatehgroup.util.Constants
import kotlinx.android.synthetic.main.layout_product_item.view.*
import javax.inject.Inject

class ProductAdapter
@Inject constructor(
    private val requestManager: RequestManager
):ListAdapter<Product,ProductAdapter.ProductViewHolder>(diffCallable) {
    companion object{
        val diffCallable = object :DiffUtil.ItemCallback<Product>(){
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean  = oldItem.productId == newItem.productId

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_product_item,parent,false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.productDescription.text = it.productDesc
            holder.productTitle.text = it.productTitle
            requestManager
                .load(Constants.BASE_IMAGE_URL+it.imageName)
                .into(holder.productImageView)
        }
    }

    class ProductViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var productImageView:ImageView
        var productTitle:TextView
        var productDescription:TextView
        init {
            productTitle = itemView.products_item_title_text_view
            productDescription = itemView.products_item_description_text_view
            productImageView = itemView.products_item_image_image_view

        }
    }
}