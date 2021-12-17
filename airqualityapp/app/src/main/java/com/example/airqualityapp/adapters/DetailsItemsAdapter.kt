package com.example.airqualityapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.airqualityapp.R
import com.example.airqualityapp.activities.DetailsListActivity

import com.example.airqualityapp.model.Detail
import kotlinx.android.synthetic.main.details_item_list.view.*


open class DetailsItemsAdapter ( private val context: Context, private var list: ArrayList<Detail>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.details_item_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {
            holder.itemView.detail_item_temperature.text = model.temperature.toString()
            holder.itemView.detail_item_humidity.text = model.humidity.toString()
            holder.itemView.detail_item_no2.text = model.no2.toString()
            holder.itemView.detail_item_o3.text = model.o3.toString()
            holder.itemView.detail_item_pm1.text = model.pm1.toString()
            holder.itemView.detail_item_pm25.text = model.pm25.toString()
            holder.itemView.detail_item_pm10.text = model.pm10.toString()
            holder.itemView.detail_item_co.text = model.co.toString()
            holder.itemView.detail_item_h2s.text = model.h2s.toString()
            holder.itemView.detail_item_ambientTemperature.text = model.ambientTemperature.toString()
            holder.itemView.detail_item_ambientHumidity.text = model.ambientHumidity.toString()
            holder.itemView.detail_item_ambientPressure.text = model.ambientPressure.toString()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

}