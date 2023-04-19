package com.example.countryapp.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.countryapp.model.Country

@Dao
interface CountryDao {

    //Data Access Object
    //Insert -> INSERT INTO
    //suspend -> coroutine,pause & resume
    //vararg -> multiple country objects
    //List<Long> -> primary keys

    @Insert
    suspend fun insertAll(vararg countries : Country) : List<Long>

    @Query(value = "SELECT * FROM country")
    suspend fun getAllCountries() : List<Country>

    @Query(value = "SELECT * FROM country WHERE uuid = :countryId")
    suspend fun getCountry(countryId : Int) : Country

    @Query(value ="DELETE FROM country")
    suspend fun deleteAllCountries()

}