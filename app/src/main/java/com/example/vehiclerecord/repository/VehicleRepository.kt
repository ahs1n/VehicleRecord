package com.example.vehiclerecord.repository

import androidx.lifecycle.LiveData
import com.example.vehiclerecord.data.VehicleDao
import com.example.vehiclerecord.model.Vehicle

class VehicleRepository(private val vehicleDao: VehicleDao) {

    val readAllData: LiveData<List<Vehicle>> = vehicleDao.readAllData()
}