package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coord (
  @Json(name="lon" ) var lon : Double? = 0.0,
  @Json(name="lat" ) var lat : Double? = 0.0)