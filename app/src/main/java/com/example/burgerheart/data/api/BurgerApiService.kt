package com.example.burgerheart.data.api

import com.example.burgerheart.model.Burger
import retrofit2.http.GET
import retrofit2.http.Path

interface BurgerApiService {
    @GET("burgers")
    suspend fun getBurgers(): List<Burger>

    @GET("burgers/{burgerId}")
    suspend fun getBurgerById(@Path("burgerId") burgerId: String): Burger
}


