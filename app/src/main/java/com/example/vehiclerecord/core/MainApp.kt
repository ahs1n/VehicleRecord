package com.example.vehiclerecord.core

import android.app.Application
import android.view.View
import com.example.vehiclerecord.model.Users
import com.jakewharton.threetenabp.AndroidThreeTen

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()

        //Initiate DateTime
        AndroidThreeTen.init(this)
        //Initializ App info
        appInfo = AppInfo(this)
    }

    companion object {
        const val PROJECT_NAME = "Vehicle Record"
        const val SYNC_LOGIN = "sync_login"
        const val _IP = "https://vcoe1.aku.edu" // .LIVE server

        //    public static final String _IP = "http://f38158";// .TEST server
        //public static final String _IP = "http://43.245.131.159:8080";// .TEST server
        const val _HOST_URL = _IP + "/naunehal/api/" // .TEST server;
        const val _SERVER_URL = "sync.php"
        const val _SERVER_GET_URL = "getData.php"
        const val _UPDATE_URL = _IP + "/naunehal/app/"
        var DeviceURL = "devices.php"
        var appInfo: AppInfo? = null
        var users: Users? = null
        var admin = false
        fun hideSystemUI(decorView: View) {
            // Enables regular immersive mode.
            // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
            // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_IMMERSIVE // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // Hide the nav bar and status bar
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }
}