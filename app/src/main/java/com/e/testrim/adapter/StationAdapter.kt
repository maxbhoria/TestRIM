package com.e.testrim.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.e.testrim.R
import com.e.testrim.models.ObjStationData

internal class StationAdapter(private var stationDataList: List<ObjStationData>) :
    RecyclerView.Adapter<StationAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvArrivalTime: TextView = view.findViewById(R.id.tv_arrival_time)
        var tvStationName: TextView = view.findViewById(R.id.tv_station_name)
        var tvDestinationTime: TextView = view.findViewById(R.id.tv_destination_time)
        var tvDestinationName: TextView = view.findViewById(R.id.tv_destination_name)
        var tvArrivalLateTime: TextView = view.findViewById(R.id.tv_arrival_late_time)
        var tvDepartureLateTime: TextView = view.findViewById(R.id.tv_departure_late_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.station_items, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = stationDataList[position]
        if(item.Scharrival.equals("00:00")){
            holder.tvArrivalTime.text = item.Origintime
        }else {
            holder.tvArrivalTime.text = item.Scharrival
        }
        holder.tvStationName.text = item.Stationfullname
        holder.tvDestinationTime.text = item.Destinationtime
        holder.tvDestinationName.text = item.Destination
        val lateTime = item.Late.toInt()
        if (lateTime > 0) {
            holder.tvArrivalLateTime.text = "+"+item.Late+"min"
            holder.tvDepartureLateTime.text = "+"+item.Late+"min"
        } else {
            holder.tvArrivalLateTime.visibility = View.GONE
            holder.tvDepartureLateTime.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return stationDataList.size
    }
}