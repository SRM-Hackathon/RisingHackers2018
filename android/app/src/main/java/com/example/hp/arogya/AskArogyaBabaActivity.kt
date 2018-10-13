package com.example.hp.arogya

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_ask_arogya_baba.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.app_bar_ask_arogya_baba.*
import kotlinx.android.synthetic.main.content_ask_arogya_baba.*
import org.json.JSONException
import org.json.JSONObject

class AskArogyaBabaActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask_arogya_baba)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val topics = arrayOf("--Select topic--", "Almonds", "Apple", "Avocado", "Beans", "Eggs", "Health", "Quinoa", "Salmon", "Seeds", "Watermelon", "Yogurt")
        val topicsAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, topics)
        topic.adapter = topicsAdapter


    }

    @SuppressLint("SetTextI18n")
    fun ask_question(view: View) {

        if(topic.selectedItem.toString() != "--Select topic--" && !question.text.isEmpty()) {

            Toast.makeText(this, "Asking Arogya Baba...", Toast.LENGTH_LONG).show()
            answer.text = "Loading...."

            val file = topic.selectedItem.toString().toLowerCase()

            val url = "https://arogya-baba-django.herokuapp.com/?file=" + file  +
                    "&question=" + devoidOfSpace(question.text.toString())
            Log.d("Arogya", url)
            val loginRequest = object: JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
                try {

                    val ans = response.getString("answer")

                    answer.text = ans

                } catch (e: JSONException) {
                    Log.d("JSON", "EXC: " + e.localizedMessage)
                }
            }, Response.ErrorListener { error ->
                Log.d("ERROR", "Could not send mail: $error")

            }) {

                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }
            }
            loginRequest.retryPolicy = DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

            Volley.newRequestQueue(this).add(loginRequest)

        } else {
            Toast.makeText(this, "Completet the form!", Toast.LENGTH_SHORT).show()
        }

    }

    fun devoidOfSpace(url: String): String {
        return url.replace("\\s".toRegex(), "%20")
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
