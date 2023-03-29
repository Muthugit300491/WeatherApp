package com.example.weatherapp.common

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import com.example.weatherapp.view.MainActivity.Companion.location


open class AppLocationService(context: Context) : Service(),
    LocationListener {
    private var locationManager: LocationManager? =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @SuppressLint("MissingPermission")
    fun getLocation(provider: String?): Location? {
        if (locationManager?.isProviderEnabled(provider!!)!!) {
            provider?.let {
                //locationManager?.requestLocationUpdates(provider, 2000, 1, this)
                locationManager?.requestLocationUpdates(
                    it, 2000, 1.toFloat(), this
                )
                locationManager?.getLastKnownLocation(it)?.let { mLocation ->
                    location = mLocation
                    return mLocation
                }
            }
        }
        return null
    }

    override fun onLocationChanged(mLocation: Location) {
        location = mLocation


    }

    override fun onProviderDisabled(provider: String) {

    }

    override fun onProviderEnabled(provider: String) {}
    override fun onStatusChanged(
        provider: String, status: Int, extras: Bundle
    ) {

    }

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    companion object {
        private const val MIN_DISTANCE_FOR_UPDATE: Long = 0
        private const val MIN_TIME_FOR_UPDATE = 100 * 2.toLong()
    }
}

