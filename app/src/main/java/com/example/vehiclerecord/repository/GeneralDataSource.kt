package com.example.vehiclerecord.repository

import com.example.vehiclerecord.model.Users

interface GeneralDataSource {

    /*
    * For login Start
    * */
    suspend fun getLoginInformation(username: String, password: String): Users?
    /*
    * For login End
    * */
}