package com.example.weatherapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherDetails (

  @Json(name="coord")
  var coord: Coord?= Coord(),
  @Json(name="weather")
  var weather    : List<Weather> = listOf<Weather>(),
  @Json(name="base")
  var base:String?= "",
  @Json(name = "main")
  var main: Main?= Main(),
  @Json(name="visibility")
  var visibility : Int?= 0,
  @Json(name="wind"       )
  var wind: Wind?= Wind(),
  @Json(name="clouds")
  var clouds     : Clouds?= Clouds(),
  @Json(name="dt")
  var dt: Int?= 0,
  @Json(name="sys")
  var sys: Sys?= Sys(),
  @Json(name="timezone")
  var timezone: Int?= 0,
  @Json(name="id")
  var id: Int?= 0,
  @Json(name="name")
  var name: String?= "",
  @Json(name="cod")
  var cod: Int?= 0

)