package com.example.countryapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "country")
data class Country(
    @ColumnInfo(name = "name")
    @SerializedName("name")
    var countryName: String?,
    @ColumnInfo(name = "region")
    @SerializedName("region")
    var countryRegion : String?,
    @ColumnInfo(name = "capital")
    @SerializedName("capital")
    var countryCapital: String?,
    @ColumnInfo(name = "currency")
    @SerializedName("currency")
    var countryCurrency: String?,
    @ColumnInfo(name = "language")
    @SerializedName("language")
    var countryLanguage: String?,
    @ColumnInfo(name = "flag")
    @SerializedName("flag")
    var imageUrl: String?
){
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}