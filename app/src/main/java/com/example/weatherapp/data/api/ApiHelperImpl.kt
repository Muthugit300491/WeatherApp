package com.example.weatherapp.data.api


import com.kotlin.employee.utils.Utils
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

//class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override fun getLatLngWeather(lat:String,lng:String)= flow {
        emit(apiService.getlatlngWeather(lat,lng, Utils.API_KEY,Utils.units))
    }

    override fun getCityWeather(city:String)=flow{
        emit(apiService.getCityWeather(city,Utils.API_KEY,Utils.units))
    }


}


