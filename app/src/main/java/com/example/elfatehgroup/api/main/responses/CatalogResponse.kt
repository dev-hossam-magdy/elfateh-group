package com.example.elfatehgroup.api.main.responses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CatalogResponse(



	@field:SerializedName("per_page")
	@Expose
	val perPage: Int? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("data")
	@Expose
	val data: List<CatalogItem> ,

	@field:SerializedName("last_page")
	@Expose
	val lastPage: Int,


	@field:SerializedName("from")
	@Expose
	val from: Int? = null,

	@field:SerializedName("to")
	@Expose
	val to: Int? = null,


	@field:SerializedName("current_page")
	@Expose
	val currentPage: Int? = null
)

@Entity(tableName = "catalog_table")
data class CatalogItem(

	@ColumnInfo(name = "image_name")
	@field:SerializedName("image_name")
	@Expose
	val imageName: String,

	@ColumnInfo(name = "yarn_name")
	@field:SerializedName("yarn_name")
	@Expose
	val yarnName: String,

	@PrimaryKey
	@ColumnInfo(name = "catalog_id")
	@field:SerializedName("catalogue_id")
	@Expose
	val catalogId: String,

	@ColumnInfo(name = "updated_at")
	@field:SerializedName("updated_at")
	@Expose
	val updatedAt: String

)
