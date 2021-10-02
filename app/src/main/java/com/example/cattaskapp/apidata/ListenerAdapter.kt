package com.example.cattaskapp.apidata

interface ListenerAdapter {
    fun openFragmentCat(url:String)
    suspend fun getNewItem()
}