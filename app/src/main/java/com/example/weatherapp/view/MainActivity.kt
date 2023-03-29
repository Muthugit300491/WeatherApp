package com.example.weatherapp.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.productapp.utils.checkGPS
import com.example.productapp.utils.getPdialog
import com.example.productapp.utils.showToast
import com.example.weatherapp.R
import com.example.weatherapp.data.model.WeatherDetails
import com.example.weatherapp.data.repository.Resource
import com.example.weatherapp.data.repository.StatusCalled
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.viewmodel.WeatherViewMoel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var pDialog: AlertDialog
    private lateinit var activityMainBinding: ActivityMainBinding
    private val weatherViewMoel:WeatherViewMoel by viewModels()
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var lat=0.00
    private var lng=0.00

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        pDialog = getPdialog()
        pDialog.dismiss()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        getLocationPerission()


    }
    fun getLocationPerission(){
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
               this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )

            return
        }
        checkGPS()

        Handler().postDelayed(object :Runnable{
            @SuppressLint("MissingPermission")
            override fun run() {
                fusedLocationProviderClient?.lastLocation?.addOnSuccessListener { location : Location? ->

                    if(location!=null){
                        lat= location.latitude
                        lng=location.longitude
                        Log.e("location",""+lat+""+lng)
                        getWeather(lat.toString(),lng.toString())
                    }

                }
            }

        },1000)


    }

    fun getWeather(lat:String,lng:String){
        val livedata=MutableLiveData<Resource<WeatherDetails>>()
        weatherViewMoel.getWeatherlatlng(lat,lng,livedata).observe(this, Observer {
            when (it.status) {
                StatusCalled.SUCCESS -> {
                    pDialog.dismiss()
                    if(it.data?.weather?.size!!>0){

                    }else{
//                        categoryAdapter?.addItems(arrayListOf())
                        showToast("No Product Details  found")
                    }
                }
                StatusCalled.LOADING -> {
                    pDialog.show()
                }
                StatusCalled.ERROR -> {
                    //Handle Error
                    pDialog.dismiss()
                    showToast(it.message)
                }
            }
        })

    }





    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if ((ContextCompat.checkSelfPermission(
                            this, Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED)
                    ) {
                        getLocationPerission()
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show() }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }


    companion object {
        private const val REQUEST_CODE = 1
        var location: Location? = null
    }
}