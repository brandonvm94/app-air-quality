package com.example.airqualityapp.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import com.example.airqualityapp.R
import com.example.airqualityapp.services.AuthClass
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )

        btn_login.setOnClickListener {
            signInUser()
        }

        btn_register.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun signInUser() {
        val email: String = login_email.text.toString().trim { it <= ' ' }
        val password: String = login_password.text.toString().trim { it <= ' ' }

        if (validateForm(email, password)) {
            showProgressDialog("Iniciando Sesión")

            val loginInfo = JSONObject()
            loginInfo.put("email", email)
            loginInfo.put("password", password)

            AuthClass().signInUser(this@LoginActivity, loginInfo)
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        return if (TextUtils.isEmpty(email)) {
            showSnackBarMessage("Debe ingresar el correo electrónico del usuario.", true)
            false
        } else if (TextUtils.isEmpty(password)) {
            showSnackBarMessage("Debe ingresar la contraseña del usuario.", true)
            false
        } else {
            true
        }
    }

    fun signInSuccess() {
        hideProgressDialog()
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        this.finish()
    }

    fun signInError(message: String) {
        hideProgressDialog()
        showSnackBarMessage(message, true)
    }
}