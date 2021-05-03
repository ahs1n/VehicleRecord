package com.example.vehiclerecord.utils.extension

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.telephony.TelephonyManager


fun isNetworkConnected(context: Context): Boolean {
    var result = false
    val connMgr =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connMgr.activeNetwork ?: return false
        val networkInfo = connMgr.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            networkInfo.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkInfo.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkInfo.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connMgr.run {
            connMgr.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
    }

    return result
}

@SuppressLint("HardwareIds")
fun getIMEIInfo(context: Context): String {
    val mTelephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        mTelephony.imei
    } else {
        mTelephony.deviceId
    }
}

fun isGPSEnabled(context: Context): Boolean {
    val mLocManager = (context.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
    return mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}