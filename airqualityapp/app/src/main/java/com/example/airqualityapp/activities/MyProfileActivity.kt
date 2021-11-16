package com.example.airqualityapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.example.airqualityapp.R
import com.example.airqualityapp.model.User
import com.example.airqualityapp.services.AuthClass
import com.example.airqualityapp.services.MyProfileClass
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class MyProfileActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        setupActionBar()

        AuthClass().getCurrentSession(this@MyProfileActivity)

        my_profile_actualizar_btn.setOnClickListener{
            updateMyInfo()
        }

        my_profile_change_password_btn.setOnClickListener{
            goToChangePasswordView()
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_my_profile_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = "Mi Perfil"
        }

        toolbar_my_profile_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun goToChangePasswordView() {
        startActivity(Intent(this@MyProfileActivity, ChangePasswordActivity::class.java))
    }

    // Get My Info Methods
    fun setUserInfo(myInfo: User) {
        my_profile_name.setText(myInfo.name)
        my_profile_email.setText(myInfo.email)
    }

    // Update My Info Methods
    private fun updateMyInfo() {
        val name: String = my_profile_name.text.toString().trim { it <= ' ' }
        val email: String = my_profile_email.text.toString().trim { it <= ' ' }

        if (validateForm(name, email)) {
            showProgressDialog("Actualizando usuario")

            val userInfo = JSONObject()
            userInfo.put("name", name)
            userInfo.put("email", email)

            MyProfileClass().updateMyInfo(this@MyProfileActivity, userInfo)
        }
    }

    fun updateMyInfoSuccess() {
        hideProgressDialog()
        setResult(Activity.RESULT_OK)
        finish()
    }

    fun updateMyInfoError(message: String) {
        hideProgressDialog()
        showSnackBarMessage(message, true)
    }

    private fun validateForm(name: String, email: String): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                showSnackBarMessage("Debe de ingresar un nombre de usuario.", true)
                false
            }
            TextUtils.isEmpty(email) -> {
                showSnackBarMessage("Debe de ingresar un correo electrónico de usuario.", true)
                false
            }
            else -> {
                true
            }
        }
    }
}