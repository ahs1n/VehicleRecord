package com.example.vehiclerecord.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vehiclerecord.model.Vehicle

@Dao
interface VehicleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addVehicle(vehicle: Vehicle)

    @Query("SELECT * FROM vehicle_table WHERE vehilceNo = :vehicleNo ORDER BY id ASC")
    fun readAllData(vehicleNo: String): LiveData<List<Vehicle>>

}