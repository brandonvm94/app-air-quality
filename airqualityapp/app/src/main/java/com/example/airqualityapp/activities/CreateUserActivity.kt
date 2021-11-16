package com.example.airqualityapp.activities

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import com.example.airqualityapp.R
import com.example.airqualityapp.services.UsersClass
import kotlinx.android.synthetic.main.activity_create_user.*
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_users_list.*
import org.json.JSONObject

class CreateUserActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        setupActionBar()

        create_user_btn.setOnClickListener {
            createUser()
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_create_user_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = "Crear Usuario"
        }

        toolbar_create_user_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun createUser() {
        val name: String = create_user_name.text.toString().trim { it <= ' ' }
        val email: String = create_user_email.text.toString().trim { it <= ' ' }
        val password: String = create_user_password.text.toString().trim { it <= ' ' }

        if (validateForm(name, email, password)) {
            showProgressDialog("Creando Usuario")

            val userInfo = JSONObject()
            userInfo.put("name", name)
            userInfo.put("email", email)
            userInfo.put("password", password)
            userInfo.put("roleId", 2)

            UsersClass().createUser(this@CreateUserActivity, userInfo)
        }
    }

    fun createUserSuccess() {
        hideProgressDialog()
        setResult(Activity.RESULT_OK)
        finish()
    }

    fun createUserError(message: String) {
        hideProgressDialog()
        showSnackBarMessage(message, true)
    }

    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                showSnackBarMessage("Debe de ingresar un nombre de usuario.", true)
                false
            }
            TextUtils.isEmpty(email) -> {
                showSnackBarMessage("Debe de ingresar un correo electrónico de usuario.", true)
                false
            }
            password.length < 6 -> {
                showSnackBarMessage("La contraseña debe tener minimo 6 caracteres.", true)
                false
            }
            TextUtils.isEmpty(password) -> {
                showSnackBarMessage("Debe ingresar una contraseña.", true)
                false
            }
            else -> {
                true
            }
        }
    }
}