package com.example.airqualityapp.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import com.example.airqualityapp.R
import com.example.airqualityapp.model.Sensor
import com.example.airqualityapp.model.User
import com.example.airqualityapp.services.AuthClass
import com.example.airqualityapp.services.SensorClass
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.awaitMapLoad
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import com.google.android.gms.maps.model.Marker
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()
        setUpMap()

        nav_view.setNavigationItemSelectedListener(this)
        AuthClass().getCurrentSession(this@MainActivity)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            doubleBackToExit()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_my_profile -> {
                startActivityForResult(Intent(this@MainActivity, MyProfileActivity::class.java), MY_PROFILE_REQUEST_CODE)
            }

            R.id.nav_admin_users -> {
                startActivity(Intent(this@MainActivity, UsersListActivity::class.java))
            }

            R.id.nav_sign_out -> {
                showProgressDialog("Cerrando Sesión")
                AuthClass().signOutUser(this@MainActivity)
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == MY_PROFILE_REQUEST_CODE) {
            AuthClass().getCurrentSession(this@MainActivity)
        } else {
            Log.e("Cancelled", "Cancelled")
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_main_activity)
        toolbar_main_activity.setNavigationIcon(R.drawable.ic_action_navigation_menu)

        toolbar_main_activity.setNavigationOnClickListener {
            toggleDrawer()
        }
    }

    private fun toggleDrawer() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    private fun setUpMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        lifecycleScope.launchWhenCreated {
            googleMap = mapFragment.awaitMap()
            val costaRicaPosition = LatLng(9.90,-84.13)

            googleMap.awaitMapLoad()
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(costaRicaPosition))
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(7f), 2000, null)

            SensorClass().getAllSensors(this@MainActivity)
        }
    }

    fun loadSensorsSuccess(sensorsList: ArrayList<Sensor>) {
        sensorsList.forEach {
            val pos = LatLng(it.lat, it.long)
            val markerOptions = MarkerOptions().position(pos).title(it.name)
            val marker = googleMap.addMarker(markerOptions)
            marker.tag = it.id
        }

        googleMap.setOnMarkerClickListener(OnMarkerClickListener { marker ->
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val sensorId = marker.tag

            val datepickerdialog: DatePickerDialog = DatePickerDialog(this@MainActivity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val intent = Intent(this@MainActivity, SensorDetailsActivity::class.java)
                intent.putExtra("sensorId", sensorId.toString())
                intent.putExtra("sensorDate", "${dayOfMonth}/${monthOfYear}/${year}")
                startActivity(intent)
            }, year, month, day)

            datepickerdialog.show()

            false
        })
    }

    fun loadSensorsError(message: String) {
        hideProgressDialog()
        showSnackBarMessage(message, true)
    }

    fun updateUserInfoSidenav(user: User) {
        sidenav_username_text.text = user.name;
        sidenav_email_text.text = user.email;
    }

    fun signOutSuccess() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    fun signOutError(message: String) {
        hideProgressDialog()
        showSnackBarMessage(message, true)
    }

    companion object {
        const val MY_PROFILE_REQUEST_CODE: Int = 11
    }
}