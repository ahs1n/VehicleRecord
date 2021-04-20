package com.example.vehiclerecord.fragment.list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.vehiclerecord.R
import com.example.vehiclerecord.model.Vehicle
import com.example.vehiclerecord.viewmodel.VehicleViewModel
import com.kennyc.view.MultiStateView
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class ListFragment : Fragment(R.layout.fragment_list) {

    lateinit var adapter: ListAdapter
    private lateinit var mVehicleViewModel: VehicleViewModel
    var items: List<Vehicle> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        /*
        * Viewmodel initialize
        * */
        mVehicleViewModel = ViewModelProvider(this).get(VehicleViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Recyclerview
        adapter = ListAdapter()
        recyclerview.adapter = adapter

        // VehicleViewModel
        multiStateView.viewState = MultiStateView.ViewState.LOADING
        mVehicleViewModel.readAllData.observe(viewLifecycleOwner, Observer { vehicle ->
            vehicle?.let {
                if (it.isNotEmpty()) {
                    val item =
                        it.map { root -> root.copy(driverName = root.driverName.toLowerCase(Locale.ENGLISH)) }
                    items = item
                    adapter.setData(items)
                    multiStateView.viewState = MultiStateView.ViewState.CONTENT
                } else multiStateView.viewState = MultiStateView.ViewState.EMPTY
            }
        })

        /*
       * Filter listener
       * */
        txtFilter.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                multiStateView.viewState = MultiStateView.ViewState.LOADING
            }

            override fun afterTextChanged(s: Editable?) {
                if (s == null) {
                    lifecycleScope.launch {
                        delay(1000)
                        adapter.vehicleList = items
                        multiStateView.viewState = MultiStateView.ViewState.CONTENT
                    }
                } else {
                    lifecycleScope.launch {
                        delay(1000)
                        val cropItem = items
                        adapter.vehicleList = cropItem.sortedBy { it.vehilceNo }.filter {
                            it.vehilceNo.startsWith(
                                s.toString().toLowerCase(
                                    Locale.ENGLISH
                                )
                            )
                        } as ArrayList<Vehicle>
                        if (adapter.vehicleList.isNotEmpty()) {
                            adapter.notifyDataSetChanged()
                            multiStateView.viewState = MultiStateView.ViewState.CONTENT
                        } else
                            multiStateView.viewState = MultiStateView.ViewState.EMPTY
                    }
                }
            }

        })
    }
}