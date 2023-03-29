package com.example.productapp.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.LocationManager
import android.provider.Settings
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.weatherapp.R
import com.example.weatherapp.common.AppLocationService
import com.example.weatherapp.view.MainActivity.Companion.location
import java.util.*

fun Context.showToast(message: String?, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, length).show()
}

fun toolBar(window: Window, mActivity: Activity) {

    // clear FLAG_TRANSLUCENT_STATUS flag:
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    // finally change the color
    window.statusBarColor = ContextCompat.getColor(mActivity, R
        .color.white)


    //window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

    //mActivity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    // edited here
    //mActivity.window.statusBarColor = Color.BLACK
}


fun Activity.getPdialog(loading: String = "Loading...Please Wait..."): AlertDialog {
    /*val dialogBuilder = AlertDialog.Builder(this)
    dialogBuilder.setMessage(loading)
    dialogBuilder.setCancelable(false)
    val mAlertDialog = dialogBuilder.create()
    mAlertDialog.setCanceledOnTouchOutside(false)
    return if (!mAlertDialog.isShowing) {
        mAlertDialog.show()
        mAlertDialog
    } else {
        mAlertDialog
    }*/
    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
    val mView: View = layoutInflater.inflate(R.layout.loader, null)

    builder.setView(mView)
    val alertDialog: AlertDialog = builder.create()
    Objects.requireNonNull(alertDialog.window)!!.setBackgroundDrawable(
        ColorDrawable(
            Color.TRANSPARENT
        )
    )
    alertDialog.setCanceledOnTouchOutside(false)
    alertDialog.show()
    return alertDialog
}

fun Activity.checkGPS() {
    val appLocationService = AppLocationService(this)
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        showSettingsAlert()
    } else {
        location = appLocationService.getLocation(LocationManager.GPS_PROVIDER)
    }
}

fun Activity.showSettingsAlert() {
    val alertDialog = AlertDialog.Builder(this)
    alertDialog.setTitle("SETTINGS")
    alertDialog.setMessage("Enable Location Provider! Go to settings menu?")
    alertDialog.setPositiveButton("Settings") { _, _ ->
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }
    alertDialog.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
    alertDialog.show()
}


