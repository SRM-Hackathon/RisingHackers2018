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
                    val sellIntent = Intent(this, PedometerActivity::class.java)
                    sellIntent.putExtra("id", id)
                    sellIntent.putExtra("first", first)
                    sellIntent.putExtra("name", name)
                    sellIntent.putExtra("pageType", "2")
                    startActivity(sellIntent)
                }
                "FoodScanner" -> {
                    val buyIntent = Intent(this, FoodScannerActivity::class.java)
                    buyIntent.putExtra("id", id)
                    buyIntent.putExtra("first", first)
                    buyIntent.putExtra("name", name)
                    buyIntent.putExtra("pageType", "1")
                    startActivity(buyIntent)
                }
                "Profile" -> {
                    val rentIntent = Intent(this, ProfileActivity::class.java)
                    rentIntent.putExtra("id", id)
                    rentIntent.putExtra("first", first)
                    rentIntent.putExtra("name", name)
                    rentIntent.putExtra("pageType", "2")
                    startActivity(rentIntent)
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
