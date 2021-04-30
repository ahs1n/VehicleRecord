package com.example.vehiclerecord.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.vehiclerecord.data.VehicleDatabase
import com.example.vehiclerecord.model.Vehicle
import com.example.vehiclerecord.repository.GeneralRepository
import com.example.vehiclerecord.repository.VehicleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VehicleViewModel(application: GeneralRepository) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Vehicle>>
    private val repository: VehicleRepository


    init {
        val vehicleDao = VehicleDatabase.getDatabase(application).tabletsDao()
        repository = VehicleRepository(vehicleDao)
        readAllData = repository.readAllData
    }

    fun addVehicle(vehicle: Vehicle) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addVehicle(vehicle)
        }
    }

    fun updateVehicle(vehicle: Vehicle) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateVehicle(vehicle)
        }
    }
}