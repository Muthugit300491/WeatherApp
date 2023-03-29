package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Main (
  @Json(name="temp")
  var temp: Double? = 0.0,
  @Json(name="feels_like")
  var feelsLike : Double? = 0.0,
  @Json(name="temp_min" )
  var tempMin   : Double? = 0.0,
  @Json(name = "temp_max")
  var tempMax   : Double? = 0.0,
  @Json(name="pressure" )
  var pressure  : Int? = 0,
  @Json(name="humidity")
  var humidity  : Int? = 0,
  @Json(name="sea_level")
  var seaLevel  : Int? = 0,
  @Json(name="grnd_level")
  var grndLevel : Int? = 0
)