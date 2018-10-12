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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_set_goal.*
import kotlinx.android.synthetic.main.activity_set_goal.*
import kotlinx.android.synthetic.main.app_bar_set_goal.*
import kotlinx.android.synthetic.main.nav_menu.*
import org.json.JSONObject

class SetGoalActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val url = "https://arogya2018.herokuapp.com/api/account/Pedo"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_goal)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        nav_view.setNavigationItemSelectedListener(this)


        val colorValue = ContextCompat.getColor(this, R.color.colorPrimary)
        pedometer.setBackgroundColor(colorValue)

        pedometer_icon.setColorFilter(Color.rgb(255, 255, 255))
        pedometer_text.setTextColor(Color.rgb(255, 255, 255))

        bmi_bmr.setOnClickListener {
            val intent1 = Intent(this,BmiBmrActivity::class.java)
            val uid = intent.getStringExtra("uid")
            intent1.putExtra("uid",uid)
            val token = intent.getStringExtra("token")
            intent1.putExtra("token",token)
            startActivity(intent1)
        }
        profile.setOnClickListener {
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
        food_scanner.setOnClickListener {
            val intent1 = Intent(this,FoodScannerActivity::class.java)
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

        setGoal.setOnClickListener {
            val jsonobj = JSONObject()
            val uid = intent.getStringExtra("uid")
            val token = intent.getStringExtra("token")
            jsonobj.put("uid", uid)
            jsonobj.put("cb", "")
            jsonobj.put("nos",totalSteps.text)


            val que = Volley.newRequestQueue(this@SetGoalActivity)
            val request = JsonObjectRequest(Request.Method.POST,url,jsonobj,
                    Response.Listener { response ->

                        val jsonStatus = response.getBoolean("success")

                        if(jsonStatus) {

                            Toast.makeText(this, "Goal is set!", Toast.LENGTH_SHORT).show()


                        } else {

                            val message = response.getString("message")
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                        }

                    }, Response.ErrorListener {
                Log.d("Login", it.message)
            })
            que.add(request)
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
