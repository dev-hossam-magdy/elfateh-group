package com.example.elfatehgroup.api.main

import androidx.lifecycle.LiveData
import com.example.blogapplication.util.GenericApiResponse
import com.example.elfatehgroup.api.main.responses.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path

//https://elfateh-group.com/api/product/1
interface ElfatehGroupApi {

    @GET("product/{page}")
    fun getProductsList(
        @Path("page") pageNumber:Int
    ):LiveData<GenericApiResponse<ProductResponse>>

}