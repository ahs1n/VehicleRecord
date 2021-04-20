package com.example.vehiclerecord.fragment.add

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vehiclerecord.R
import com.example.vehiclerecord.model.Vehicle
import com.example.vehiclerecord.viewmodel.VehicleViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {

    private lateinit var mVehicleViewModel: VehicleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mVehicleViewModel = ViewModelProvider(this).get(VehicleViewModel::class.java)

        view.addTimeIn.setOnClickListener {
            insertDataToDatabase()
        }

        view.addTimeOut.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        val driverName = addDriverName.text.toString()
        val vehicleNo = addVehicleNo.text.toString()
        val date = addDate.text.toString()
        val meterIn = addMeterIn.text.toString()
        val meterOut = addMeterOut.text.toString()
        val remarks = addRemarks.text.toString()

        if (inputCheck(driverName, vehicleNo, date, meterIn, meterOut, remarks)) {
            // Create Vehicle Object
            val vehicle = Vehicle(
                0,
                driverName = driverName,
                vehilceNo = vehicleNo,
                todayDate = date,
                meter_in = meterIn,
                meter_out = meterOut,
                remarks = remarks
            )
            // Add Data to Database
            mVehicleViewModel.addVehicle(vehicle)
            Toast.makeText(requireContext(), "Successfully TimeIn", Toast.LENGTH_LONG).show()
            // Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please filled all fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(
        driverName: String,
        vehicleNo: String,
        date: String,
        meterIn: String,
        meterOut: String,
        remarks: String
    ): Boolean {
        return !(TextUtils.isEmpty(driverName) && TextUtils.isEmpty(vehicleNo) && TextUtils.isEmpty(
            date
        ) && TextUtils.isEmpty(meterIn) && TextUtils.isEmpty(meterOut) && TextUtils.isEmpty(remarks))
    }
}