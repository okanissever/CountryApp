package com.example.countryapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

class CustomSharedPreferences {

    companion object{
        private val PREFERENCES_TIME = "preferences_time"
        private var sharedPreferences: SharedPreferences ?=null
        @Volatile private var instance: CustomSharedPreferences?= null

        operator fun invoke(context: Context): CustomSharedPreferences= instance ?: synchronized(Any()){
            instance?: makeCustomSharedPrefences(context).also {
                instance = it
            }
        }
        private fun makeCustomSharedPrefences(context: Context) : CustomSharedPreferences{
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }

    }

    fun saveTime(time : Long){
        sharedPreferences?.edit(commit = true){
            putLong(PREFERENCES_TIME,time)
        }
    }

    fun getTime() = sharedPreferences?.getLong(PREFERENCES_TIME,0)

}