package com.example.vehiclerecord.repository

import com.example.vehiclerecord.data.VehicleDatabase
import com.example.vehiclerecord.model.Users

open class GeneralRepository(private val db: VehicleDatabase) : GeneralDataSource {

    override suspend fun getLoginInformation(username: String, password: String): Users? {
        TODO("Not yet implemented")
    }

}