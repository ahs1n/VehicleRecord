package com.example.vehiclerecord.fragment.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vehiclerecord.R
import com.example.vehiclerecord.model.Vehicle
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var vehicleList = emptyList<Vehicle>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_driver_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return vehicleList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = vehicleList[position]

        holder.itemView.driverName_txt.text = currentItem.driverName
        holder.itemView.vehicleNo_txt.text = currentItem.vehilceNo
        holder.itemView.meterIn_txt.text = currentItem.meter_in
        holder.itemView.meterOut_txt.text = currentItem.meter_out
    }

    fun setData(vehicle: List<Vehicle>) {
        this.vehicleList = vehicle
        notifyDataSetChanged()
    }
}