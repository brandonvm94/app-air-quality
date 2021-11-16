package com.example.airqualityapp.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import com.example.airqualityapp.R
import com.example.airqualityapp.services.AuthClass
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupActionBar()

        btn_registrar.setOnClickListener {
            registerUser()
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(register_toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        register_toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun registerUser() {
        // Getting values from the form
        val name: String = register_name.text.toString().trim { it <= ' ' }
        val email: String = register_email.text.toString().trim { it <= ' ' }
        val password: String = register_password.text.toString().trim { it <= ' ' }
        val confirmPassword: String = register_confirm_password.text.toString().trim { it <= ' ' }

        if (validateForm(name, email, password, confirmPassword)) {
            showProgressDialog("Registrando usuario")

            val userInfo = JSONObject()
            userInfo.put("name", name)
            userInfo.put("email", email)
            userInfo.put("password", password)

            AuthClass().signUpUser(this@RegisterActivity, userInfo)
        }
    }

    private fun validateForm(name: String, email: String, password: String, confirmPassword: String): Boolean {
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
            !password.equals(confirmPassword) -> {
                showSnackBarMessage("Las contraseñas no son iguales.", true)
                false
            }
            else -> {
                true
            }
        }
    }

    fun userRegisteredSuccess() {
        hideProgressDialog()
        showSnackBarMessage("Usuario registrado correctamente.", false)
        finish()
    }

    fun userRegisteredError(message: String) {
        hideProgressDialog()
        showSnackBarMessage(message, true)
    }
}