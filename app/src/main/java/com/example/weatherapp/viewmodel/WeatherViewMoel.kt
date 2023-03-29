package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.api.NetworkHelper
import com.example.weatherapp.data.model.WeatherDetails
import com.example.weatherapp.data.repository.MainRepository
import com.example.weatherapp.data.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewMoel @Inject constructor(private val mainRepository: MainRepository,
                                          private val networkHelper: NetworkHelper):ViewModel(){

   private  fun getLatLngWeather(lat:String, lng:String,
        mLiveData: MutableLiveData<Resource<WeatherDetails>>
    ) {
        viewModelScope.launch {
            mLiveData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getlatlngWeather(lat,lng)
                    .catch { e ->
                        mLiveData.postValue(Resource.error(e.toString(), null))
                    }
                    .collect {
                        mLiveData.postValue(Resource.success(it))
                    }
            } else {
                mLiveData.postValue(Resource.error("No internet connection", null))
            }
        }
    }

    private fun getWeatherCity(city:String,
                                 mLiveData: MutableLiveData<Resource<WeatherDetails>>
    ) {
        viewModelScope.launch {
            mLiveData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getCityWeather(city)
                    .catch { e ->
                        mLiveData.postValue(Resource.error(e.toString(), null))
                    }
                    .collect {
                        mLiveData.postValue(Resource.success(it))
                    }
            } else {
                mLiveData.postValue(Resource.error("No internet connection", null))
            }
        }
    }

    fun getWeatherlatlng(lat: String,lng: String,
        mLiveData: MutableLiveData<Resource<WeatherDetails>>
    ): LiveData<Resource<WeatherDetails>> {
        getLatLngWeather(lat,lng,mLiveData)
        return mLiveData
    }

     fun getWeathercity(city: String,
                               mLiveData: MutableLiveData<Resource<WeatherDetails>>
    ): LiveData<Resource<WeatherDetails>> {
        getWeatherCity(city,mLiveData)
        return mLiveData
    }

}