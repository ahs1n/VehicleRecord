package com.example.vehiclerecord.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "vehicle_table")
data class Vehicle(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val vehilceNo: String,
    val driverName: String,
    val meter_in: String,
    val meter_out: String,
    val remarks: String,
    val todayDate: String
//    val picName: String

) : Parcelable