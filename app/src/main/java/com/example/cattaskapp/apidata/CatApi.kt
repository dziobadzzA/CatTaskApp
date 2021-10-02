package com.example.cattaskapp.apidata

import retrofit2.http.GET

interface CatApi {
    @GET("/v1/images/search?api-key=3437333-17ce-4a96-9ed1-716360a579b8&limit=10")
    suspend fun getListOfCats(): List<Cat>
}