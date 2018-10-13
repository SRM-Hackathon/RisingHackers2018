package com.example.hp.arogya

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.content_edit_profile.*
import kotlinx.android.synthetic.main.app_bar_edit_profile.*
import kotlinx.android.synthetic.main.nav_menu.*
import org.json.JSONObject
import java.util.*

class EditProfileActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val url = "https://arogya2018.herokuapp.com/api/account/edit"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

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
            val intent1 = Intent(this,FoodScannerActivity::class.java)
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
        profile.setOnClickListener {
            val intent1 = Intent(this,ProfileActivity::class.java)
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



        val types = arrayOf("Select Gender","Male","Female","Others")
        changeGender.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,types)
        changeGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {


            override fun onNothingSelected(parent: AdapterView<*>?) {
                changeGenderView.text = "Select your gender"
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                changeGenderView.text = types.get(position)
            }

        }





        editBtn.setOnClickListener {


            val jsonobj = JSONObject()

            if(changeName.text.toString().isEmpty() && changePassword.text.toString().isEmpty()  && changeGenderView.text.toString() == "Select Gender") {
                Toast.makeText(this,"Please Select any one Field",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,"ok",Toast.LENGTH_SHORT).show()
                val uid = intent.getStringExtra("uid")
                val token = intent.getStringExtra("token")
                jsonobj.put("uid", uid)
                if(changeName.text.toString().isNotEmpty()) {
                    jsonobj.put("Name", changeName.text)
                }else {
                    jsonobj.put("Name","")
                }
                if(changePassword.text.toString().isNotEmpty()) {
                    jsonobj.put("password", changePassword.text)
                }else {
                    jsonobj.put("password","")
                }
                if(changeGenderView.text.toString() != "Select Gender") {
                    jsonobj.put("gender", changeGenderView.text[0])
                }else {
                    jsonobj.put("gender","")
                }


                val que = Volley.newRequestQueue(this@EditProfileActivity)
                val request = JsonObjectRequest(Request.Method.POST, url, jsonobj,
                        Response.Listener {

                            response ->
                            val jsonStatus = response.getBoolean("success")
                            if(jsonStatus) {
                                Toast.makeText(this, "Edit succesfully!", Toast.LENGTH_SHORT).show()
                            } else {
                                val jsonMessage = response.getString("message")
                                Toast.makeText(this, jsonMessage, Toast.LENGTH_SHORT).show()
                            }

                        }, Response.ErrorListener {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                })
                que.add(request)

            }

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
