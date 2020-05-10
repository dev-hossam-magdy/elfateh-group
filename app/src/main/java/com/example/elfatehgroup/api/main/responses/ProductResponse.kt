package com.example.elfatehgroup.api.main.responses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductResponse(

	@field:SerializedName("first_page_url")
	@Expose
	val firstPageUrl: String,

	@field:SerializedName("path")
	@Expose
	val path: String ,

	@field:SerializedName("per_page")
	@Expose
	val perPage: Int ,

	@field:SerializedName("total")
	@Expose
	val total: Int,

	@field:SerializedName("data")
	@Expose
	val data: List<Product>,

	@field:SerializedName("last_page")
	@Expose
	val lastPage: Int,


	@field:SerializedName("from")
	@Expose
	val from: Int,

	@field:SerializedName("to")
	@Expose
	val to: Int



)

@Entity(tableName = "products_table")
data class Product(

	@field:SerializedName("product_desc")
	@Expose
	@ColumnInfo(name = "product_des")
	val productDesc: String ,

	@field:SerializedName("image_name")
	@Expose
	@ColumnInfo(name = "image")
	val imageName: String,

	@field:SerializedName("updated_at")
	@Expose
	@ColumnInfo(name = "updated_at")
	val updatedAt: String,

	@field:SerializedName("product_id")
	@Expose
	@PrimaryKey
	@ColumnInfo(name = "product_id")
	val productId: String,

	@field:SerializedName("created_at")
	@Expose
	@ColumnInfo(name = "created_at")
	val createdAt: String,

	@field:SerializedName("product_title")
	@Expose
	@ColumnInfo(name = "product_title")
	val productTitle: String
)
