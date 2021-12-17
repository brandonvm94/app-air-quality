package com.example.airqualityapp.activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.example.airqualityapp.R
import com.example.airqualityapp.model.Detail
import com.example.airqualityapp.services.DetailsClass
import kotlinx.android.synthetic.main.activity_sensor_details.*

class SensorDetailsActivity : BaseActivity() {

    private lateinit var sensorId: String
    private lateinit var sensorDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_details)

        setupActionBar()

        sensorId = intent.getStringExtra("sensorId").toString()
        sensorDate = intent.getStringExtra("sensorDate").toString()

        DetailsClass().getDetail(this@SensorDetailsActivity, sensorId, sensorDate)
        showProgressDialog("Cargando detalles del sensor")
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_sensor_details_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = "Detalles del sensor"
        }

        toolbar_sensor_details_activity.setNavigationOnClickListener { onBackPressed() }
    }

    fun getSensorDetailsSuccess(detailInfo: Detail) {
        hideProgressDialog()
        println(detailInfo.ambientHumidity)

        sensor_details_section.visibility = View.VISIBLE
        no_details_message.visibility = View.GONE

        detail_item_temperature.text = "Temperatura: ${detailInfo.temperature.toString()}"
        detail_item_humidity.text = "Humedad: ${detailInfo.humidity.toString()}"
        detail_item_no2.text = "No2: ${detailInfo.no2.toString()}"
        detail_item_o3.text = "O3: ${detailInfo.no.toString()}"
        detail_item_no.text = "NO: ${detailInfo.so2.toString()}"
        detail_item_pm1.text = "PM1: ${detailInfo.pm1.toString()}"
        detail_item_pm10.text = "PM10: ${detailInfo.pm10.toString()}"
        detail_item_pm25.text = "PM25: ${detailInfo.pm25.toString()}"
        detail_item_co.text = "CO: ${detailInfo.co.toString()}"
        detail_item_h2s.text = "H2S: ${detailInfo.h2s.toString()}"
        detail_item_ambientTemperature.text = "Temperatura Ambiente: ${detailInfo.ambientTemperature.toString()}"
        detail_item_ambientHumidity.text = "Humedad Ambiente: ${detailInfo.ambientHumidity.toString()}"
        detail_item_ambientPressure.text = "Presión Ambiente: ${detailInfo.ambientPressure.toString()}"
    }

    fun getSensorDetailsError(message: String) {
        hideProgressDialog()
        showSnackBarMessage(message, true)

        sensor_details_section.visibility = View.GONE
        no_details_message.visibility = View.VISIBLE
    }



}