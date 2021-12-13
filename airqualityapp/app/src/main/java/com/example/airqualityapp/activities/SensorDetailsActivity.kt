package com.example.airqualityapp.activities

import android.os.Bundle
import com.example.airqualityapp.R
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

        println(sensorId)
        println(sensorDate)
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_sensor_details_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = "Sensor Detalles"
        }

        toolbar_sensor_details_activity.setNavigationOnClickListener { onBackPressed() }
    }
}