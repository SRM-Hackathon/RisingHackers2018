package com.example.hp.arogya

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.DialogFragment
import android.app.IntentService
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.hp.arogya.R.styleable.AlertDialog
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject
import java.util.*
import android.widget.TextView
import android.widget.DatePicker
import com.example.hp.arogya.R.id.*
import java.util.regex.Pattern


class SignUpActivity : AppCompatActivity() {

    val url = "https://arogya2018.herokuapp.com/api/account/signup"
    lateinit var genderSpinner: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

//        type = findViewById<Spinner>(gender)
//        genderSpinner = findViewById<TextView>(genderView)
        val types = arrayOf("Select Gender","Male","Female","Others")
        gender.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,types)
        gender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {


            override fun onNothingSelected(parent: AdapterView<*>?) {
                genderView.text = "Select your gender"
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                genderView.text = types.get(position)
            }

        }
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        Dob.setOnClickListener {
            val dob = DatePickerDialog(this,android.R.style.Theme_DeviceDefault_Light,DatePickerDialog.OnDateSetListener { view: DatePicker, year1:Int, month1:Int, day1:Int ->
                DobView.setText(""+day1.toString()+"/"+month1.toString()+"/"+year1.toString())
            },year,month,day)
            dob.show()
        }
        val jsonobj = JSONObject()

        signUp.setOnClickListener {
            if(!name.text.isEmpty() && !email.text.isEmpty() && !password.text.isEmpty() && !confirmPassword.text.isEmpty()
            && DobView.text != "Not selected" && genderView.text.toString() != "Select Gender") {

                if(!isEmailValid(email.text.toString())) {
                    Toast.makeText(this, "Invalid email address!", Toast.LENGTH_SHORT).show()
                } else if (password.text.toString() != confirmPassword.text.toString()) {
                    Toast.makeText(this,"enter same password",Toast.LENGTH_SHORT).show()
                } else {

                    jsonobj.put("Name", name.text)
                    jsonobj.put("email", email.text)
                    jsonobj.put("password", password.text)
                    jsonobj.put("dob", DobView.text)
                    jsonobj.put("gender", genderView.text[0])

                    val que = Volley.newRequestQueue(this@SignUpActivity)
                    val request = JsonObjectRequest(Request.Method.POST, url, jsonobj,
                            Response.Listener { response ->

                                val jsonStatus = response.getBoolean("success")

                                if(jsonStatus) {
                                    Toast.makeText(this, "Signed up succesfully!", Toast.LENGTH_SHORT).show()
                                    val gotoLogin = Intent(this, MainActivity::class.java)
                                    gotoLogin.putExtra("email", email.text.toString())
                                    gotoLogin.putExtra("password", password.text.toString())
                                    startActivity(gotoLogin)
                                } else {
                                    val jsonMessage = response.getString("message")
                                    Toast.makeText(this, jsonMessage, Toast.LENGTH_SHORT).show()
                                }
                            }, Response.ErrorListener {
                        Log.d("Signup", it.message)
                    })
                    que.add(request)

                }

            } else {
                Toast.makeText(this, "Complete the form!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun isEmailValid(email: String): Boolean {
        return Pattern.compile(
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }
}
