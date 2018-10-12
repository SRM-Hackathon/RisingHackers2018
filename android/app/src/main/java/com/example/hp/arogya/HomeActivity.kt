package com.example.hp.arogya

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONException

class HomeActivity : AppCompatActivity() {

    lateinit var adapter: CategoryAdapter

    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        sp = getSharedPreferences("login", Context.MODE_PRIVATE)

        val id = sp.getString("id", "")
        val name = sp.getString("name", "")
        val first = sp.getString("first", "")


        adapter = CategoryAdapter(this, DataServiceHome.categories) { category ->
            val title = category.title.replace(" ", "")
            when (title) {
                "Pedometer" -> {
                    val pedointent = Intent(this, PedometerActivity::class.java)
                    val uid = intent.getStringExtra("uid")
                    pedointent.putExtra("uid",uid)
                    val token = intent.getStringExtra("token")
                    pedointent.putExtra("token",token)
                    startActivity(pedointent)
                }
                "FoodScanner" -> {
                    val foodintent = Intent(this, FoodScannerActivity::class.java)
                    val uid = intent.getStringExtra("uid")
                    foodintent.putExtra("uid",uid)
                    val token = intent.getStringExtra("token")
                    foodintent.putExtra("token",token)
                    startActivity(foodintent)
                }
                "Profile" -> {
                    val profileIntent = Intent(this, ProfileActivity::class.java)
                    val uid = intent.getStringExtra("uid")
                    profileIntent.putExtra("uid",uid)
                    val token = intent.getStringExtra("token")
                    profileIntent.putExtra("token",token)
                    startActivity(profileIntent)
                }
                "Bmi/Bmr" -> {
                    val bmiIntent = Intent(this, BmiBmrActivity::class.java)
                    val uid = intent.getStringExtra("uid")
                    bmiIntent.putExtra("uid",uid)
                    val token = intent.getStringExtra("token")
                    bmiIntent.putExtra("token",token)
                    startActivity(bmiIntent)
                }
                "ArogyaBaba" -> {
                    val arogyaBabaIntent = Intent(this,AskArogyaBabaActivity::class.java)
                    val uid = intent.getStringExtra("uid")
                    arogyaBabaIntent.putExtra("uid",uid)
                    val token = intent.getStringExtra("token")
                    arogyaBabaIntent.putExtra("token",token)
                    startActivity(arogyaBabaIntent)
                }
                else -> Toast.makeText(this, "Yeh page nhi bana hai", Toast.LENGTH_SHORT).show()
            }
        }
        categoryList.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        categoryList.layoutManager = layoutManager
        categoryList.setHasFixedSize(true)


    }


}
