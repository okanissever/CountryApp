package com.example.countryapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countryapp.model.Country
import com.example.countryapp.service.CountryApiService
import com.example.countryapp.service.CountryDatabase
import com.example.countryapp.util.CustomSharedPreferences
import kotlinx.coroutines.*

class FeedViewModel(application: Application) : BaseViewModel(application) {
    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()
    private var customPreferences = CustomSharedPreferences(getApplication())
    private var job : Job? = null
    private var refreshTime = 10*60*1000*1000*1000L

    private val service = CountryApiService.countryApiService

    fun refreshData(){

        val updateTime = customPreferences.getTime()
        if(updateTime!=null && updateTime != 0L && System.nanoTime() - updateTime<refreshTime){
            getDataFromSql()
        }
        else{
            getDataFromApi()
        }

    }

    fun refreshFromAPI(){
        getDataFromApi()
    }

    private fun getDataFromSql(){
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            //.makeText(getApplication(),"Countries From SQLite",Toast.LENGTH_LONG).show()
            countryLoading.postValue(false)
            countryError.postValue(false)
        }
    }

    private fun getDataFromApi(){
        job = viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                countryLoading.value = true
            }
            try{
                val response = service.getCountries()
                withContext(Dispatchers.Main){
                    if (response.isSuccessful){
                        response.body()?.let {
                            showCountries(it)
                            storeInSQLite(it)
                            //Toast.makeText(getApplication(),"Countries From API",Toast.LENGTH_LONG).show()
                        }
                    }
                    else{
                        countryError.value = true
                    }
                    countryLoading.value = false
                }
            }
            catch (e: Exception){
                e.printStackTrace()
                println("Exception type: ${e::class.simpleName}")
                println("Exception message: ${e.message}")
                countryError.postValue(true)
                countryLoading.postValue(false)
            }
        }
    }
    private fun showCountries(list : List<Country>){
        countries.value = list
    }

    private fun storeInSQLite(list : List<Country>){
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listlong = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i<list.size){
                list[i].uuid = listlong[i].toInt()
                i++
            }

        }
        customPreferences.saveTime(System.nanoTime())
    }


}