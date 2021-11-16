package com.example.airqualityapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.airqualityapp.R
import com.example.airqualityapp.adapters.UserItemsAdapter
import com.example.airqualityapp.model.User
import com.example.airqualityapp.services.UsersClass
import kotlinx.android.synthetic.main.activity_create_user.*
import kotlinx.android.synthetic.main.activity_users_list.*

class UsersListActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)

        setupActionBar()
        loadUsers()

        fab_create_user.setOnClickListener {
            startActivityForResult(Intent(this@UsersListActivity, CreateUserActivity::class.java), CREATE_USER_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == CREATE_USER_REQUEST_CODE) {
            loadUsers()
        }
        else {
            Log.e("Cancelled", "Cancelled")
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_users_list_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = "Administrar Usuarios"
        }

        toolbar_users_list_activity.setNavigationOnClickListener { onBackPressed() }
    }

    // GET Users methods
    private fun loadUsers() {
        showProgressDialog("Cargando usuarios")
        UsersClass().getAllUsers(this@UsersListActivity)
    }

    fun loadUsersSuccess(usersList: ArrayList<User>) {
        hideProgressDialog()

        if (usersList.size > 0) {

            rv_users_list.visibility = View.VISIBLE
            no_users_message.visibility = View.GONE

            rv_users_list.layoutManager = LinearLayoutManager(this@UsersListActivity)
            rv_users_list.setHasFixedSize(true)

            val adapter = UserItemsAdapter(this@UsersListActivity, usersList)
            rv_users_list.adapter = adapter
        } else {
            rv_users_list.visibility = View.GONE
            no_users_message.visibility = View.VISIBLE
        }
    }

    fun loadUsersError(message: String) {
        hideProgressDialog()
        showSnackBarMessage(message, true)
    }

    // UPDATE User Methods
    fun goToEditUser(user: User) {
        val intent = Intent(this@UsersListActivity, UpdateUserActivity::class.java)
        intent.putExtra("userSelected", user)
        startActivityForResult(intent, CREATE_USER_REQUEST_CODE)
    }

    // DELETE User Methods
    fun deleteUser(id: String) {
        showProgressDialog("Eliminando Usuario")
        UsersClass().deleteUser(this@UsersListActivity, id)
    }

    fun deleteUserSuccess() {
        hideProgressDialog()
        loadUsers()
    }

    fun deleteUserError(message: String) {
        hideProgressDialog()
        showSnackBarMessage(message, true)
    }

    companion object {
        const val CREATE_USER_REQUEST_CODE: Int = 12
    }
}