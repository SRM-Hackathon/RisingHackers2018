package com.example.hp.arogya

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val url = "https://arogya2018.herokuapp.com/api/account/signin"
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")

        loginName.setText(email)
        loginPassword.setText(password)

        goToSign.setOnClickListener {
            val gotoSignup = Intent(this, SignUpActivity::class.java)
            startActivity(gotoSignup)
        }

        login_login_btn.setOnClickListener {

            val jsonobj = JSONObject()

            jsonobj.put("email",loginName.text)
            jsonobj.put("password",loginPassword.text)

            val que = Volley.newRequestQueue(this@MainActivity)
            val request = JsonObjectRequest(Request.Method.POST,url,jsonobj,
                    Response.Listener { response ->

                        val jsonStatus = response.getBoolean("success")

                        if(jsonStatus) {

                            Toast.makeText(this, "Logged in successfully!", Toast.LENGTH_SHORT).show()

                            val token = response.getString("token")

                        } else {

                            val message = response.getString("message")
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                        }

                    },Response.ErrorListener {
                        Log.d("Login", it.message)
            })
            que.add(request)

        }

    }
}
