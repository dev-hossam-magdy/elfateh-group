package com.example.elfatehgroup.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.elfatehgroup.R
import com.example.elfatehgroup.api.main.responses.CatalogItem
import com.example.elfatehgroup.util.Constants
import kotlinx.android.synthetic.main.layout_catalog_item.view.*
import javax.inject.Inject

class CatalogItemAdapter @Inject constructor(private val requestManager: RequestManager) :
    androidx.recyclerview.widget.ListAdapter<CatalogItem, CatalogItemAdapter.CatalogItemViewHolder>(
        diffUtil
    ) {
    companion object {
        var diffUtil = object : DiffUtil.ItemCallback<CatalogItem>() {
            override fun areItemsTheSame(oldItem: CatalogItem, newItem: CatalogItem): Boolean =
                oldItem.catalogId == newItem.catalogId

            override fun areContentsTheSame(oldItem: CatalogItem, newItem: CatalogItem): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_catalog_item, parent, false)
        return CatalogItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatalogItemViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { catalogItem ->
            requestManager
                .load(Constants.BASE_IMAGE_URL + catalogItem.imageName)
                .into(holder.catalogItemImageView)
            holder.catalogItemNumberTextView.text = catalogItem.yarnName

        }

    }

    class CatalogItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var catalogItemImageView: ImageView
        var catalogItemNumberTextView: TextView

        init {
            catalogItemImageView = itemView.catalog_item_image_view
            catalogItemNumberTextView = itemView.catalog_item_number_text_view
        }
    }
}