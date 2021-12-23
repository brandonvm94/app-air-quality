package com.example.airqualityapp.services

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.airqualityapp.activities.MainActivity
import com.example.airqualityapp.model.Sensor
import com.example.airqualityapp.utils.Constants
import org.json.JSONObject

class SensorClass {

    fun getAllSensors(activity: MainActivity) {
        val queue = Volley.newRequestQueue(activity)
        val url = Constants.API_URL + Constants.SENSORS

        val getAllSensorsRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val sensorsList: ArrayList<Sensor> = ArrayList()
                for (i in 0 until response.length()) {
                    val item: JSONObject = response.getJSONObject(i)
                    val sensorId = item.getString("id")
                    val sensorName = item.getString("name")
                    val sensorLat = item.getDouble("lat")
                    val sensorLong = item.getDouble("long")
                    val sensor = Sensor(sensorId, sensorName, sensorLat, sensorLong)
                    sensorsList.add(sensor)
                }
                activity.loadSensorsSuccess(sensorsList)
            },
            { error ->
                Log.e(
                    activity.javaClass.simpleName,
                    error.toString(),
                    error,
                )
                activity.loadSensorsError(error.toString())
            }
        )

        queue.add(getAllSensorsRequest)
    }

}