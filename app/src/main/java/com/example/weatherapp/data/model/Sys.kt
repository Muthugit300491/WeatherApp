package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sys (

  @Json(name="country")
  var country : String? = "",
  @Json(name = "sunrise")
  var sunrise : Int?    = 0,
  @Json(name="sunset")
  var sunset  : Int?    = 0

)