package com.example.cattaskapp.apidata

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.Exception

object CatApiImpl {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("https://api.thecatapi.com")
        .build()

    private val CatService = retrofit.create(CatApi::class.java)

    suspend fun getListOfCats(): List<Cat> {
         return withContext(Dispatchers.IO) {
             try {
                 CatService.getListOfCats()
             }
             catch (e:Exception) {
                 listOf<Cat>()
             }
        }
    }

}