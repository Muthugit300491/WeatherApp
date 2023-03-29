package com.example.weatherapp.data.api


import com.example.weatherapp.data.model.WeatherDetails
import kotlinx.coroutines.flow.Flow


interface ApiHelper {

    fun getLatLngWeather(lat:String,lng:String): Flow<WeatherDetails>
    fun getCityWeather(city:String):Flow<WeatherDetails>


}