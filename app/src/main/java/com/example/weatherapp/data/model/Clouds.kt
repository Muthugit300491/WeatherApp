package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Clouds (
  @Json(name = "all" )
  var all : Int? = 0
)