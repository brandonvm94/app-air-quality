package com.example.airqualityapp.activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.airqualityapp.R
import com.example.airqualityapp.adapters.DetailsItemsAdapter
import com.example.airqualityapp.model.Detail

import com.example.airqualityapp.services.DetailsClass
import kotlinx.android.synthetic.main.activity_sensor_details.*
import kotlinx.android.synthetic.main.activity_sensor_details.rv_details_list
import kotlinx.android.synthetic.main.activity_sensor_details.toolbar_sensor_details_activity
import kotlinx.android.synthetic.main.activity_update_user.*
import kotlinx.android.synthetic.main.details_item_list.*

class DetailsListActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_details)

            setupActionBar()
            loadDetails()


        }
    private fun setupActionBar() {
        setSupportActionBar(toolbar_sensor_details_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = "Detalle del sensor"
        }

        toolbar_sensor_details_activity.setNavigationOnClickListener { onBackPressed() }
    }
    private fun loadDetails() {
        showProgressDialog("Cargando datos")
        DetailsClass().getAllDetails(this@DetailsListActivity)
    }
    fun loadDetailsSuccess(detailsList: ArrayList<Detail>) {
        hideProgressDialog()

        if (detailsList.size > 0) {

            rv_details_list.visibility = View.VISIBLE
            no_detail_message.visibility = View.GONE

            rv_details_list.layoutManager = LinearLayoutManager(this@DetailsListActivity)
            rv_details_list.setHasFixedSize(true)

            val adapter = DetailsItemsAdapter(this@DetailsListActivity, detailsList)
            rv_details_list.adapter = adapter
        } else {
            rv_details_list.visibility = View.GONE
            no_detail_message.visibility = View.VISIBLE
        }
    }
    fun loadDetailsError(message: String) {
        hideProgressDialog()
        showSnackBarMessage(message, true)
    }
    fun getDetailError(message: String) {
        hideProgressDialog()
        showSnackBarMessage(message, true)
    }
    fun getDetailSuccess(detailInfo: Detail) {
        hideProgressDialog()
        detail_item_temperature.setText(detailInfo.temperature)
        detail_item_humidity.setText(detailInfo.humidity)
        detail_item_no2.setText(detailInfo.no2.toString())
        detail_item_o3.setText(detailInfo.no.toString())
        detail_item_no.setText(detailInfo.so2.toString())
        detail_item_pm1.setText(detailInfo.pm1.toString())
        detail_item_pm10.setText(detailInfo.pm10.toString())
        detail_item_pm25.setText(detailInfo.pm25.toString())
        detail_item_co.setText(detailInfo.co.toString())
        detail_item_h2s.setText(detailInfo.h2s.toString())
        detail_item_ambientTemperature.setText(detailInfo.ambientTemperature)
        detail_item_ambientHumidity.setText(detailInfo.ambientHumidity)
        detail_item_ambientPressure.setText(detailInfo.ambientPressure)
    }
}