package com.example.burgerheart.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Burger(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String?,
    @Json(name = "images") val images: List<Image>?,
    @Json(name = "desc") val desc: String?,
    @Json(name = "ingredients") val ingredients: List<Ingredient>?,
    @Json(name = "price") val price: Double = 0.0,
    @Json(name = "veg") val veg: Boolean,
    var quantity: Int = 0
)

@JsonClass(generateAdapter = true)
data class Image(
    @Json(name = "sm") val sm: String?,
    @Json(name = "lg") val lg: String?
)

@JsonClass(generateAdapter = true)
data class Ingredient(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "img") val img: String?
)
