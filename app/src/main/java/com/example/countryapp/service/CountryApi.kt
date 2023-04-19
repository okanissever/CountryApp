package com.example.countryapp.service

import com.example.countryapp.model.Country
import retrofit2.Response
import retrofit2.http.GET

interface CountryAPI {

    //https://raw.githubusercontent.com/

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    suspend fun getCountries() : Response<List<Country>>
}