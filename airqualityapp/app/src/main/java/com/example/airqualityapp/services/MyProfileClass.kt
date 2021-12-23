package com.example.airqualityapp.services

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.airqualityapp.activities.ChangePasswordActivity
import com.example.airqualityapp.activities.MyProfileActivity
import com.example.airqualityapp.utils.Constants
import org.json.JSONObject

class MyProfileClass {
    fun updateMyInfo(activity: MyProfileActivity, userInfo: JSONObject) {
        val queue = Volley.newRequestQueue(activity)
        val url = Constants.API_URL + Constants.UPDATE_MY_INFO

        val signupRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            userInfo,
            { response ->
                activity.updateMyInfoSuccess()
            },
            { error ->
                Log.e(
                    activity.javaClass.simpleName,
                    error.toString(),
                    error,
                )
                activity.updateMyInfoError(error.toString())
            }
        )

        queue.add(signupRequest)
    }

    fun updateMyPassword(activity: ChangePasswordActivity, userInfo: JSONObject) {
        val queue = Volley.newRequestQueue(activity)
        val url = Constants.API_URL + Constants.UPDATE_PASSWORD

        val signupRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            userInfo,
            { response ->
                activity.updateMyPasswordSuccess()
            },
            { error ->
                Log.e(
                    activity.javaClass.simpleName,
                    error.toString(),
                    error,
                )
                activity.updateMyPasswordError(error.toString())
            }
        )

        queue.add(signupRequest)
    }
}