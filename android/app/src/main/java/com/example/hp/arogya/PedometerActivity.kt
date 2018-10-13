package com.example.hp.arogya

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.hp.arogya.R.id.*
import com.example.hp.arogya.listener.StepListener
import com.example.hp.arogya.utils.StepDetector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_pedometer.*
import kotlinx.android.synthetic.main.app_bar_pedometer.*
import kotlinx.android.synthetic.main.content_pedometer.*
import kotlinx.android.synthetic.main.nav_menu.*
import org.json.JSONObject

/**
 * Created by Govind on 05/25/2018.
 */
class PedometerActivity : AppCompatActivity(), SensorEventListener, StepListener, NavigationView.OnNavigationItemSelectedListener {

    val url = "https://arogya2018.herokuapp.com/api/account/pedo"
    private var simpleStepDetector: StepDetector? = null
    private var sensorManager: SensorManager? = null
    private var numSteps: Int = 0

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector!!.updateAccelerometer(event.timestamp, event.values[0], event.values[1], event.values[2])
        }
    }

    override fun step(timeNs: Long) {
        numSteps++
        tvSteps.text = numSteps.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedometer)


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout,  toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        nav_view.setNavigationItemSelectedListener(this)


        val colorValue = ContextCompat.getColor(this, R.color.colorPrimary)
        pedometer.setBackgroundColor(colorValue)

        pedometer_icon.setColorFilter(Color.rgb(255, 255, 255))
        pedometer_text.setTextColor(Color.rgb(255, 255, 255))

        bmi_bmr.setOnClickListener {
            val intent1 = Intent(this, BmiBmrActivity::class.java)
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

        // Get an instance of the SensorManager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        simpleStepDetector = StepDetector()
        simpleStepDetector!!.registerListener(this)

        btnStart.setOnClickListener(View.OnClickListener {
            numSteps = 0
            sensorManager!!.registerListener(this, sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST)
        })

        btnStop.setOnClickListener(View.OnClickListener {
            sensorManager!!.unregisterListener(this)
            val jsonobj = JSONObject()
            val temp = tvSteps.text.toString()
            val totalCalorie = temp.toFloat()/20
            Toast.makeText(this, "right", Toast.LENGTH_SHORT).show()
            val uid = intent.getStringExtra("uid")
            val token = intent.getStringExtra("token")

            jsonobj.put("uid", uid)
            jsonobj.put("cb",totalCalorie.toString())
            jsonobj.put("nos", "")


            val que = Volley.newRequestQueue(this@PedometerActivity)
            val request = JsonObjectRequest(Request.Method.POST,url,jsonobj,
                    Response.Listener { response ->

                        val jsonStatus = response.getBoolean("success")

                        if(jsonStatus) {

                            Toast.makeText(this, "ok!", Toast.LENGTH_SHORT).show()


                        } else {

                            val message = response.getString("message")
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                        }

                    }, Response.ErrorListener {

            })
            que.add(request)


        })


        btnGoal.setOnClickListener {
            val gotoSetGoal = Intent(this, SetGoalActivity::class.java)
            val uid = intent.getStringExtra("uid")
            gotoSetGoal.putExtra("uid",uid)
            val token = intent.getStringExtra("token")
            gotoSetGoal.putExtra("token",token)
            startActivity(gotoSetGoal)
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






