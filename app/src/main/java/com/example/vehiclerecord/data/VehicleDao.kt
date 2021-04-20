package com.example.vehiclerecord.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.vehiclerecord.model.Vehicle

@Dao
interface VehicleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addVehicle(vehicle: Vehicle)

    @Update
    suspend fun updateVehicle(vehicle: Vehicle)

    @Query("SELECT * FROM vehicle_table WHERE vehilceNo = :vehicleNo ORDER BY id ASC")
    fun readAllData(vehicleNo: String): LiveData<List<Vehicle>>

    @Query("SELECT * FROM vehicle_table")
    fun readAllData(): LiveData<List<Vehicle>>

}