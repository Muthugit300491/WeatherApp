package com.example.weatherapp.data.repository



import com.example.weatherapp.data.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {
    fun getlatlngWeather(lat:String,lng:String) =  apiHelper.getLatLngWeather(lat,lng)
    fun getCityWeather(city:String)=apiHelper.getCityWeather(city)

}