package com.example.weatherapp.data.api


import com.example.weatherapp.data.model.WeatherDetails
import retrofit2.http.*

interface ApiService {
    /*Flow API call */
    @GET("data/2.5/weather")
    suspend fun getlatlngWeather(
        @Query("lat") lat: String,
        @Query("lon") lng: String,
        @Query("appid") appid: String,
        @Query("units") units:String
    ): WeatherDetails

    @GET("data/2.5/weather")
    suspend fun getCityWeather(@Query("q") q: String, @Query("appid") appid: String, @Query("units") units:String): WeatherDetails

}