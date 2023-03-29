package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Wind (

  @Json(name = "speed" )
  var speed : Double? = 0.0,
  @Json(name="deg")
  var deg   : Int?    = 0,
  @Json(name ="gust")
  var gust  : Double? = null

)