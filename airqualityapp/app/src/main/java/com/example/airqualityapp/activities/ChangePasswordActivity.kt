package com.example.airqualityapp.activities

import android.os.Bundle
import android.text.TextUtils
import com.example.airqualityapp.R
import com.example.airqualityapp.services.MyProfileClass
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_my_profile.*
import org.json.JSONObject

class ChangePasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        setupActionBar()

        change_password_btn.setOnClickListener{
            updateMyPassword()
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_change_password_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = "Cambiar Contraseña"
        }

        toolbar_change_password_activity.setNavigationOnClickListener { onBackPressed() }
    }

    // Update My Password Methods
    private fun updateMyPassword() {
        val newPassword: String = change_password_new.text.toString().trim { it <= ' ' }
        val newPasswordConfirm: String = change_password_confirm.text.toString().trim { it <= ' ' }

        if (validateForm(newPassword, newPasswordConfirm)) {
            showProgressDialog("Cambiando contraseña")

            val userInfo = JSONObject()
            userInfo.put("newPassword", newPassword)

            MyProfileClass().updateMyPassword(this@ChangePasswordActivity, userInfo)
        }
    }

    private fun validateForm(newPassword: String, newPasswordConfirm: String): Boolean {
        return when {
            TextUtils.isEmpty(newPassword) -> {
                showSnackBarMessage("Debe de ingresar una nueva contraseña.", true)
                false
            }
            !newPassword.equals(newPasswordConfirm) -> {
                showSnackBarMessage("Las contraseñas no son iguales.", true)
                false
            }
            else -> {
                true
            }
        }
    }

    fun updateMyPasswordSuccess() {
        hideProgressDialog()
        finish()
    }

    fun updateMyPasswordError(message: String) {
        hideProgressDialog()
        showSnackBarMessage(message, true)
    }

}