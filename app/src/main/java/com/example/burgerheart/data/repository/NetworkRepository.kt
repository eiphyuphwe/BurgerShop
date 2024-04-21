package com.example.burgerheart.data.repository

import com.example.burgerheart.data.api.BurgerApiService
import com.example.burgerheart.model.Burger
import com.example.burgerheart.ui.viewmodels.jsonData
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//api key = 2d40d5f744msh149b2cd999f6684p12aedejsn3781da7d1bdd
val key = "2e85f61303mshddff8c530ac1dd1p10adf8jsn238db701e57e"

class NetworkRepository {
    private val apiService: BurgerApiService

    init {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-RapidAPI-Key", key.toString())
                .addHeader("X-RapidAPI-Host", "burgers-hub.p.rapidapi.com")
                .build()
            chain.proceed(request)
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://burgers-hub.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        apiService = retrofit.create(BurgerApiService::class.java)
    }

    suspend fun getBurgers(): List<Burger> {
        return apiService.getBurgers()
    }

    suspend fun getBurgerById(burgerId: String): Burger =
        apiService.getBurgerById(burgerId = burgerId.toString())

    suspend fun getBurgerLocalById(burgerId: String): Burger {
        val burgers = Gson().fromJson(jsonData, Array<Burger>::class.java).toList()
        val burger = burgers.filter {
            it.id == burgerId
        }.first()
        return burger
    }
}
