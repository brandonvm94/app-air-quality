package com.example.airqualityapp.services

import android.app.Activity
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.airqualityapp.activities.*
import com.example.airqualityapp.model.User
import com.example.airqualityapp.utils.Constants
import org.json.JSONObject


class AuthClass {
    fun signUpUser(activity: RegisterActivity, userInfo: JSONObject) {
        val queue = Volley.newRequestQueue(activity)
        val url = Constants.API_URL + Constants.SIGN_UP

        val signupRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            userInfo,
            { response ->
                activity.userRegisteredSuccess()
            },
            { error ->
                Log.e(
                    activity.javaClass.simpleName,
                    error.toString(),
                    error,
                )
                activity.userRegisteredError(error.toString())
            }
        )

        queue.add(signupRequest)
    }

    fun signInUser(activity: LoginActivity, loginInfo: JSONObject) {
        val queue = Volley.newRequestQueue(activity)
        val url = Constants.API_URL + Constants.SIGN_IN

        val loginRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            loginInfo,
            { response ->
                activity.signInSuccess()
            },
            { error ->
                Log.e(
                    activity.javaClass.simpleName,
                    error.toString(),
                    error,
                )
                activity.signInError(error.toString())
            }
        )

        queue.add(loginRequest)
    }

    fun signOutUser(activity: MainActivity) {
        val queue = Volley.newRequestQueue(activity)
        val url = Constants.API_URL + Constants.SIGN_OUT

        val signoutRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            null,
            { response ->
                activity.signOutSuccess()
            },
            { error ->
                Log.e(
                    activity.javaClass.simpleName,
                    error.toString(),
                    error,
                )
                activity.signOutError(error.toString())
            }
        )

        queue.add(signoutRequest)
    }

    fun getCurrentSession(activity: Activity) {
        val queue = Volley.newRequestQueue(activity)
        val url = Constants.API_URL + Constants.SESSION_ACTIVE

        val currentSessionRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val userId = response.getString("id");
                val userName = response.getString("name");
                val userEmail = response.getString("email");
                val userRoleId = response.getInt("roleId");
                val currentSessionUser = User(userId, userName, userEmail, userRoleId)

                when(activity) {
                    is SplashActivity -> {
                        activity.splashCurrentSessionCallback(currentSessionUser)
                    }
                    is MainActivity -> {
                        activity.updateUserInfoSidenav(currentSessionUser)
                    }
                    is MyProfileActivity -> {
                        activity.setUserInfo(currentSessionUser)
                    }
                }
            }
        ) { error ->
            Log.e(
                activity.javaClass.simpleName,
                error.toString(),
                error,
            )

            when(activity) {
                is SplashActivity -> {
                    activity.splashCurrentSessionCallback(User())
                }
            }
        }

        queue.add(currentSessionRequest)
    }
}