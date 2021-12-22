package com.example.airqualityapp.services

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.airqualityapp.activities.CreateUserActivity
import com.example.airqualityapp.activities.UpdateUserActivity
import com.example.airqualityapp.activities.UsersListActivity
import com.example.airqualityapp.model.User
import com.example.airqualityapp.utils.Constants
import org.json.JSONObject

class UsersClass {

    fun getAllUsers(activity: UsersListActivity) {
        val queue = Volley.newRequestQueue(activity)
        val url = Constants.API_URL + Constants.USERS

        val signupRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val usersList: ArrayList<User> = ArrayList()
                for (i in 0 until response.length()) {
                    val item: JSONObject = response.getJSONObject(i)
                    val userId = item.getString("id")
                    val userName = item.getString("name")
                    val userEmail = item.getString("email")
                    val userRoleId = item.getInt("roleId")
                    val user = User(userId, userName, userEmail, userRoleId)
                    usersList.add(user)
                }
                activity.loadUsersSuccess(usersList)
            },
            { error ->
                Log.e(
                    activity.javaClass.simpleName,
                    error.toString(),
                    error,
                )
                val responseBody = String(error.networkResponse.data)
                val data = JSONObject(responseBody)
                val message = data.optString("message")
                activity.loadUsersError(message)
            }
        )

        queue.add(signupRequest)
    }

    fun getUser(activity: UpdateUserActivity, userId: String) {
        val queue = Volley.newRequestQueue(activity)
        val url = Constants.API_URL + Constants.USER + "/${userId}"

        val signupRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val userId = response.getString("id");
                val userName = response.getString("name");
                val userEmail = response.getString("email");
                val userRoleId = response.getInt("roleId");
                val userInfo = User(userId, userName, userEmail, userRoleId)
                activity.getUserSuccess(userInfo)
            },
            { error ->
                Log.e(
                    activity.javaClass.simpleName,
                    error.toString(),
                    error,
                )
                val responseBody = String(error.networkResponse.data)
                val data = JSONObject(responseBody)
                val message = data.optString("message")
                activity.getUserError(message)
            }
        )

        queue.add(signupRequest)
    }

    fun createUser(activity: CreateUserActivity, userInfo: JSONObject) {
        val queue = Volley.newRequestQueue(activity)
        val url = Constants.API_URL + Constants.USER

        val signupRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            userInfo,
            { response ->
                activity.createUserSuccess()
            },
            { error ->
                Log.e(
                    activity.javaClass.simpleName,
                    error.toString(),
                    error,
                )
                val responseBody = String(error.networkResponse.data)
                val data = JSONObject(responseBody)
                val message = data.optString("message")
                activity.createUserError(message)
            }
        )

        queue.add(signupRequest)
    }

    fun editUser(activity: UpdateUserActivity, userInfo: JSONObject, userId: String) {
        val queue = Volley.newRequestQueue(activity)
        val url = Constants.API_URL + Constants.USER + "/${userId}"

        val signupRequest = JsonObjectRequest(
            Request.Method.PUT,
            url,
            userInfo,
            { response ->
                activity.editUserSuccess()
            },
            { error ->
                Log.e(
                    activity.javaClass.simpleName,
                    error.toString(),
                    error,
                )
                val responseBody = String(error.networkResponse.data)
                val data = JSONObject(responseBody)
                val message = data.optString("message")
                activity.editUserError(message)
            }
        )

        queue.add(signupRequest)
    }

    fun deleteUser(activity: UsersListActivity, userId: String) {
        val queue = Volley.newRequestQueue(activity)
        val url = Constants.API_URL + Constants.USER + "/${userId}"

        val signupRequest = JsonObjectRequest(
            Request.Method.DELETE,
            url,
            null,
            { response ->
                activity.deleteUserSuccess()
            },
            { error ->
                Log.e(
                    activity.javaClass.simpleName,
                    error.toString(),
                    error,
                )
                val responseBody = String(error.networkResponse.data)
                val data = JSONObject(responseBody)
                val message = data.optString("message")
                activity.deleteUserError(message)
            }
        )

        queue.add(signupRequest)
    }

}