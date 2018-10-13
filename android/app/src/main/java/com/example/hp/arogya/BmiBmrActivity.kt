package com.example.hp.arogya

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bmi_bmr.*
import kotlinx.android.synthetic.main.app_bar_bmi_bmr.*
import kotlinx.android.synthetic.main.content_bmi_bmr.*
import kotlinx.android.synthetic.main.nav_menu.*

class BmiBmrActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_bmr)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)


        val colorValue = ContextCompat.getColor(this, R.color.colorPrimary)
        bmi_bmr.setBackgroundColor(colorValue)

        bmi_bmr_icon.setColorFilter(Color.rgb(255, 255, 255))
        bmi_bmr_text.setTextColor(Color.rgb(255, 255, 255))

        food_scanner.setOnClickListener {
            val intent1 = Intent(this, FoodScannerActivity::class.java)
            val uid = intent.getStringExtra("uid")
            intent1.putExtra("uid", uid)
            val token = intent.getStringExtra("token")
            intent1.putExtra("token", token)
            startActivity(intent1)
        }
        profile.setOnClickListener {
            val intent1 = Intent(this, ProfileActivity::class.java)
            val uid = intent.getStringExtra("uid")
            intent1.putExtra("uid", uid)
            val token = intent.getStringExtra("token")
            intent1.putExtra("token", token)
            startActivity(intent1)
        }
        chat.setOnClickListener {
            val intent1 = Intent(this, AskArogyaBabaActivity::class.java)
            val uid = intent.getStringExtra("uid")
            intent1.putExtra("uid", uid)
            val token = intent.getStringExtra("token")
            intent1.putExtra("token", token)
            startActivity(intent1)
        }
        home_button.setOnClickListener {
            val intent1 = Intent(this, HomeActivity::class.java)
            val uid = intent.getStringExtra("uid")
            intent1.putExtra("uid", uid)
            val token = intent.getStringExtra("token")
            intent1.putExtra("token", token)
            startActivity(intent1)
        }
        pedometer.setOnClickListener {
            val intent1 = Intent(this, PedometerActivity::class.java)
            val uid = intent.getStringExtra("uid")
            intent1.putExtra("uid", uid)
            val token = intent.getStringExtra("token")
            intent1.putExtra("token", token)
            startActivity(intent1)
        }
        logout.setOnClickListener {
            val intent1 = Intent(this,MainActivity::class.java)
            startActivity(intent1)
        }




    submit_bmi.setOnClickListener  {

        if (!input_height.text.isEmpty() && !input_weight.text.isEmpty()) {

            val height = input_height.text.toString().toFloat() / 100
            val weight = input_weight.text.toString().toFloat()

            val bmi = weight / (height * height)

            val intentBmiResult = Intent(this, BmiBmrResultActivity::class.java)
            intentBmiResult.putExtra("mode", "bmi")
            intentBmiResult.putExtra("bmi", bmi.toString())
            intentBmiResult.putExtra("weight", weight.toString())
            intentBmiResult.putExtra("height", height.toString())
            val uid = intent.getStringExtra("uid")
            intentBmiResult.putExtra("uid", uid)
            val token = intent.getStringExtra("token")
            intentBmiResult.putExtra("token", token)
            startActivity(intentBmiResult)

        } else {
            Toast.makeText(this, "Complete the form!", Toast.LENGTH_SHORT).show()
        }

    }

    submit_bmr.setOnClickListener {

        val age = 19
        val gender = 'M'

        if (!input_height.text.isEmpty() && !input_weight.text.isEmpty()) {

            val height = input_height.text.toString().toFloat()
            val weight = input_weight.text.toString().toFloat()

            var calories = 10 * height + 6.25 * weight - 5 * age

            if (gender == 'M') {
                calories += 5
            } else if (gender == 'F') {
                calories -= 161
            }

            val intentBmrResult = Intent(this, BmiBmrResultActivity::class.java)
            intentBmrResult.putExtra("mode", "bmr")
            intentBmrResult.putExtra("bmr", calories.toString())
            val uid = intent.getStringExtra("uid")
            intentBmrResult.putExtra("uid", uid)
            val token = intent.getStringExtra("token")
            intentBmrResult.putExtra("token", token)
            startActivity(intentBmrResult)

        } else {
            Toast.makeText(this, "Complete the form!", Toast.LENGTH_SHORT).show()
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
