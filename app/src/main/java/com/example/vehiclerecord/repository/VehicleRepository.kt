package com.example.vehiclerecord.repository

import androidx.lifecycle.LiveData
import com.example.vehiclerecord.data.VehicleDao
import com.example.vehiclerecord.model.Vehicle

class VehicleRepository(private val vehicleDao: VehicleDao) {

    val readAllData: LiveData<List<Vehicle>> = vehicleDao.readAllData()

    fun addVehicle(vehicle: Vehicle) {
        vehicleDao.addVehicle(vehicle)
    }

    suspend fun updateVehicle(vehicle: Vehicle) {
        vehicleDao.updateVehicle(vehicle)
    }
}