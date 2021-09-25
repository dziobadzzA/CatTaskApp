package com.example.cattaskapp.apidata

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Cat(
    @Json(name = "id") var idCat: String?,
    @Json(name = "url") var urlCat: String?
)

