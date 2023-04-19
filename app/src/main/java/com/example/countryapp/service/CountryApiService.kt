package com.example.countryapp.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CountryApiService {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://raw.githubusercontent.com/")
        .build()

    val countryApiService by lazy { retrofit.create(CountryAPI::class.java) }
}