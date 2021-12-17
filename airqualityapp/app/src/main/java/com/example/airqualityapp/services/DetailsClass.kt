package com.example.airqualityapp.services
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.airqualityapp.activities.DetailsListActivity
import com.example.airqualityapp.activities.SensorDetailsActivity
import com.example.airqualityapp.model.Detail

import com.example.airqualityapp.utils.Constants
import org.json.JSONObject

class DetailsClass {

    fun getAllDetails(activity: DetailsListActivity) {
        val queue = Volley.newRequestQueue(activity)
        val url = Constants.API_URL + Constants.DETAILS

        val detailsRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val detailsList: ArrayList<Detail> = ArrayList()
                for (i in 0 until response.length()) {
                    val item: JSONObject = response.getJSONObject(i)
                    val sensorId = item.getString("sensorId")
                    val date = item.getString("date")
                    val temperature = item.getInt("temperature")
                    val humidity = item.getInt("humidity")
                    val no2 = item.getDouble("no2")
                    val o3 = item.getDouble("o3")
                    val no = item.getDouble("no")
                    val so2 = item.getDouble("so2")
                    val pm1 = item.getDouble("pm1")
                    val pm25 = item.getDouble("pm25")
                    val pm10 = item.getDouble("pm10")
                    val co = item.getDouble("co")
                    val h2s = item.getDouble("h2s")
                    val ambientTemperature = item.getInt("ambientTemperature")
                    val ambientHumidity = item.getInt("ambientHumidity")
                    val ambientPressure = item.getInt("ambientPressure")
                    val detail = Detail(sensorId, date, temperature, humidity, no2, o3, no, so2, pm1, pm25, pm10, co, h2s, ambientTemperature, ambientHumidity, ambientPressure)
                    detailsList.add(detail)
                }
                activity.loadDetailsSuccess(detailsList)
            },
            { error ->
                Log.e(
                    activity.javaClass.simpleName,
                    error.toString(),
                    error,
                )
               activity.loadDetailsError(error.toString())
            }
        )

        queue.add(detailsRequest)
    }
    fun getDetail(activity: DetailsListActivity, date: String) {
        val queue = Volley.newRequestQueue(activity)
        val url = Constants.API_URL + Constants.DATE + "/${date}"

        val detailRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val sensorId = response.getString("sensorId")
                val date = response.getString("date")
                val temperature = response.getInt("temperature")
                val humidity = response.getInt("humidity")
                val no2 = response.getDouble("no2")
                val o3 = response.getDouble("o3")
                val no = response.getDouble("no")
                val so2 = response.getDouble("so2")
                val pm1 = response.getDouble("pm1")
                val pm25 = response.getDouble("pm25")
                val pm10 = response.getDouble("pm10")
                val co = response.getDouble("co")
                val h2s = response.getDouble("h2s")
                val ambientTemperature = response.getInt("ambientTemperature")
                val ambientHumidity = response.getInt("ambientHumidity")
                val ambientPressure = response.getInt("ambientPressure")
                val detailInfo = Detail(sensorId, date, temperature, humidity, no2, o3, no, so2, pm1, pm25, pm10, co, h2s, ambientTemperature, ambientHumidity, ambientPressure)
                activity.getDetailSuccess(detailInfo)
            },
            { error ->
                Log.e(
                    activity.javaClass.simpleName,
                    error.toString(),
                    error,
                )
                activity.getDetailError(error.toString())
            }
        )

        queue.add(detailRequest)
    }
}