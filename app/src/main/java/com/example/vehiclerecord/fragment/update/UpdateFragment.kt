package com.example.vehiclerecord.fragment.update

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vehiclerecord.R
import com.example.vehiclerecord.model.Vehicle
import com.example.vehiclerecord.viewmodel.VehicleViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mVehicleViewModel: VehicleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mVehicleViewModel = ViewModelProvider(this).get(VehicleViewModel::class.java)

        view.updateDriverName.setText(args.currentVehicle.driverName)
        view.updateVehicleNo.setText(args.currentVehicle.vehilceNo)
        view.updateDate.setText(args.currentVehicle.todayDate)
        view.updateMeterIn.setText(args.currentVehicle.meter_in)
        view.updateMeterOut.setText(args.currentVehicle.meter_out)
        view.updateRemarks.setText(args.currentVehicle.remarks)

        view.updateTimeOut.setOnClickListener {
            updateItem()
        }

        return view
    }

    private fun updateItem() {
        val driverName = updateDriverName.text.toString()
        val vehicleNo = updateVehicleNo.text.toString()
        val date = updateDate.text.toString()
        val meterIn = updateMeterIn.text.toString()
        val meterOut = updateMeterOut.text.toString()
        val remarks = updateRemarks.text.toString()

        if (inputCheck(driverName, vehicleNo, date, meterIn, meterOut, remarks)) {
            //Create Vehicle Object
            val updateVehicle = Vehicle(
                args.currentVehicle.id,
                driverName = driverName,
                vehilceNo = vehicleNo,
                todayDate = date,
                meter_in = meterIn,
                meter_out = meterOut,
                remarks = remarks
            )
            //Update Current Vehicle
            mVehicleViewModel.updateVehicle(updateVehicle)
            Toast.makeText(requireContext(), "Successfully TimeOut!", Toast.LENGTH_SHORT).show()
            //Navigate Back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please Enter Meter Out Reading!", Toast.LENGTH_SHORT)
                .show()
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