package com.example.hp.arogya

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.app_bar_profile.*
import kotlinx.android.synthetic.main.content_profile.*
import kotlinx.android.synthetic.main.nav_menu.*
import org.json.JSONException
import org.json.JSONObject

class ProfileActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val url = "https://arogya2018.herokuapp.com/api/account/profile"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout,  toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        nav_view.setNavigationItemSelectedListener(this)


        val colorValue = ContextCompat.getColor(this, R.color.colorPrimary)
        profile.setBackgroundColor(colorValue)

        profile_icon.setColorFilter(Color.rgb(255, 255, 255))
        profile_text.setTextColor(Color.rgb(255, 255, 255))

        bmi_bmr.setOnClickListener {
            val intent1 = Intent(this, BmiBmrActivity::class.java)
            val uid = intent.getStringExtra("uid")
            intent1.putExtra("uid",uid)
            val token = intent.getStringExtra("token")
            intent1.putExtra("token",token)
            startActivity(intent1)
        }
        food_scanner.setOnClickListener {
            val intent1 = Intent(this,ProfileActivity::class.java)
            val uid = intent.getStringExtra("uid")
            intent1.putExtra("uid",uid)
            val token = intent.getStringExtra("token")
            intent1.putExtra("token",token)
            startActivity(intent1)
        }
        chat.setOnClickListener {
            val intent1 = Intent(this,AskArogyaBabaActivity::class.java)
            val uid = intent.getStringExtra("uid")
            intent1.putExtra("uid",uid)
            val token = intent.getStringExtra("token")
            intent1.putExtra("token",token)
            startActivity(intent1)
        }
        home_button.setOnClickListener {
            val intent1 = Intent(this,HomeActivity::class.java)
            val uid = intent.getStringExtra("uid")
            intent1.putExtra("uid",uid)
            val token = intent.getStringExtra("token")
            intent1.putExtra("token",token)
            startActivity(intent1)
        }
        pedometer.setOnClickListener {
            val intent1 = Intent(this,PedometerActivity::class.java)
            val uid = intent.getStringExtra("uid")
            intent1.putExtra("uid",uid)
            val token = intent.getStringExtra("token")
            intent1.putExtra("token",token)
            startActivity(intent1)
        }
        logout.setOnClickListener {
            val intent1 = Intent(this,MainActivity::class.java)
            startActivity(intent1)
        }

        val jsonobj = JSONObject()
        val uid = intent.getStringExtra("uid")
        val token = intent.getStringExtra("token")
        jsonobj.put("uid", uid)
        val loginRequest = object : JsonObjectRequest(Method.POST, url, jsonobj, Response.Listener { response ->
            try {
                nameView.setText(response.getString("Name"))
                emailView.setText(response.getString("email"))
                genderView1.setText(response.getString("gender"))
                dobView1.setText(response.getString("dob"))
                Toast.makeText(this,"ok",Toast.LENGTH_SHORT).show()


            } catch (e: JSONException) {
                Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
                Log.d("JSON", "EXC: " + e.localizedMessage)
            }
        }, Response.ErrorListener { error ->
            Log.d("ERROR", "Could not login user: $error")
        }) {

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }
        Volley.newRequestQueue(this).add(loginRequest)


        goToEditProfile.setOnClickListener {
            val gotoEditProfile = Intent(this, EditProfileActivity::class.java)
            gotoEditProfile.putExtra("uid",uid)
            gotoEditProfile.putExtra("token",token)
            startActivity(gotoEditProfile)
        }

    }
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
