package com.example.weatherapp.data.model

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weather (
  @Json(name = "id" )
  var id: Int?    = 0,
  @Json(name="main")
  var main: String? = "",
  @Json(name = "description")
  var description : String?= "",
  @Json(name = "icon")
  var icon: String? = ""
)