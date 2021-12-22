package com.example.airqualityapp.activities

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.airqualityapp.R
import com.example.airqualityapp.model.User
import com.example.airqualityapp.services.UsersClass
import kotlinx.android.synthetic.main.activity_create_user.*
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.activity_update_user.*
import org.json.JSONObject

class UpdateUserActivity : BaseActivity() {

    private lateinit var activityUserSelected: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        setupActionBar()

        if (intent.hasExtra("userSelected")) {
            activityUserSelected = intent.getParcelableExtra<User>("userSelected")!!
            showProgressDialog("Cargando Información del usuario")
            UsersClass().getUser(this@UpdateUserActivity, activityUserSelected.id)
        }

        update_user_btn.setOnClickListener{
            editUser()
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_update_user_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = "Editar Usuario"
        }

        toolbar_update_user_activity.setNavigationOnClickListener { onBackPressed() }
    }

    // GET User methods
    fun getUserSuccess(userInfo: User) {
        hideProgressDialog()
        edit_user_name.setText(userInfo.name)

        when (userInfo.roleId) {
            1 -> findViewById<RadioButton>(R.id.admin).isChecked = true
            else -> findViewById<RadioButton>(R.id.user).isChecked = true
        }
    }

    fun getUserError(message: String) {
        hideProgressDialog()
        showSnackBarMessage(message, true)
    }

    // UPDATE User methods
    private fun editUser() {
        val name: String = edit_user_name.text.toString().trim { it <= ' ' }

        // Theme RadioGroup
        val roleRadioGroup = findViewById<RadioGroup>(R.id.roleOptions)
        val roleRadioGroupSelectedId: Int = roleRadioGroup.getCheckedRadioButtonId()
        val roleRadioButtonSelected = findViewById<RadioButton>(roleRadioGroupSelectedId)
        var roleValue: Int;
        when (roleRadioButtonSelected.getText()) {
            "Admin" -> roleValue = 1
            else -> roleValue = 2
        }

        if (validateForm(name)) {
            showProgressDialog("Editando Usuario")

            val userInfo = JSONObject()
            userInfo.put("name", name)
            userInfo.put("email", activityUserSelected.email)
            userInfo.put("roleId", roleValue)

            UsersClass().editUser(this@UpdateUserActivity, userInfo, activityUserSelected.id)
        }
    }

    fun editUserSuccess() {
        hideProgressDialog()
        setResult(Activity.RESULT_OK)
        finish()
    }

    fun editUserError(message: String) {
        hideProgressDialog()
        showSnackBarMessage(message, true)
    }

    private fun validateForm(name: String): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                showSnackBarMessage("Debe de ingresar un nombre de usuario.", true)
                false
            }
            else -> {
                true
            }
        }
    }
}