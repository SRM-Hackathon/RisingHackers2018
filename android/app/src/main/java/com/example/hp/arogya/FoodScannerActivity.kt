package com.example.hp.arogya

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_food_scanner.*
import kotlinx.android.synthetic.main.app_bar_food_scanner.*
import kotlinx.android.synthetic.main.content_food_scanner.*
import kotlinx.android.synthetic.main.nav_menu.*
import java.io.IOException
import java.util.*
import android.support.annotation.NonNull
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.content_ask_arogya_baba.*
import kotlinx.android.synthetic.main.content_profile.*
import org.json.JSONException
import org.json.JSONObject


class FoodScannerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var filepath: Uri? = null
    private val PICK_IMAGE_REQUEST = 1234

    internal var storage: FirebaseStorage?=null
    internal var storageReference: StorageReference?=null
    private lateinit var mStorageRef:StorageReference

    var category = arrayOf("","","")
    var s = "false"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_scanner)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout,  toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        nav_view.setNavigationItemSelectedListener(this)


        val colorValue = ContextCompat.getColor(this, R.color.colorPrimary)
        food_scanner.setBackgroundColor(colorValue)

        food_scanner_icon.setColorFilter(Color.rgb(255, 255, 255))
        food_scanner_text.setTextColor(Color.rgb(255, 255, 255))

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

        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference



        selectImage.setOnClickListener {
            showFileChooser()
        }
        uploadImage.setOnClickListener {
            uploadFile()
        }

        submit_btn.setOnClickListener {

            if(s == "true") {
                var cal = 0
                cal = category[1].toInt()
                val date = category[2]
                val name = category[0]
                Toast.makeText(this,name.toString(),Toast.LENGTH_LONG).show()
                cal = cal * totalQuantity.text.toString().toInt()
                var url = "https://arogya2018.herokuapp.com/api/account/daily"
                val uid = intent.getStringExtra("uid")
                val jsonobj = JSONObject()
                jsonobj.put("uid",uid.toString())
                jsonobj.put("cal",cal.toInt())
                jsonobj.put("day",date.toInt())

                val loginRequest = object : JsonObjectRequest(Method.POST, url, jsonobj, Response.Listener { response ->
                    try {
                        textCalorie.setText("No. of Calories "+cal.toString())
                        url = "https://arogya2018.herokuapp.com/api/account/fetchbmr"
                        val uid = "5bc110facb58930030b87f9b"
                        val loginRequest = object : JsonObjectRequest(Method.POST, url, jsonobj, Response.Listener { response ->
                            try {
                                val bmr = response.getString("bmr")
                                textCalorie.setText(cal.toString()+"/"+bmr.toString())

                            }catch (e: JSONException) {
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




            }catch (e: JSONException) {
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
        }else {
                Toast.makeText(this,"Loading...",Toast.LENGTH_LONG).show()
                Toast.makeText(this,"Loading...",Toast.LENGTH_LONG).show()

            }
            }
        }


    private fun uploadFile() {

        var downurl: Uri

        if (filepath != null) {
            val c = Calendar.getInstance()
            val day = c.get(Calendar.DAY_OF_MONTH)

            val hashMap:HashMap<Int,Array<String>> = HashMap<Int,Array<String>>()
            hashMap.put(0, arrayOf("aloo beans","340",day.toString()))
            hashMap.put(1,arrayOf("banana","89",day.toString()))
            hashMap.put(2,arrayOf("besan ladoo","173",day.toString()))
            hashMap.put(3,arrayOf("bhindi sabji","33",day.toString()))
            hashMap.put(4,arrayOf("chicken curry","240",day.toString()))
            hashMap.put(5,arrayOf("chole","123",day.toString()))
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Loading...")
            progressDialog.show()

            val imageRef = storageReference!!.child("images/" + UUID.randomUUID().toString())
            Toast.makeText(this,imageRef.toString(),Toast.LENGTH_LONG).show()
            Log.d("TAG", imageRef.toString())
            imageRef.putFile(filepath!!)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, "File Uploaded", Toast.LENGTH_SHORT).show()
                        imageRef.downloadUrl.addOnSuccessListener {
                            downurl = it
                            Toast.makeText(this,downurl.toString(),Toast.LENGTH_LONG).show()
                            make_prediction(downurl)
                        }

                    }
                    .addOnFailureListener {
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, "failed", Toast.LENGTH_SHORT).show()
                    }
                    .addOnProgressListener { taskSnapShot ->
                        val progress = 100.0 * taskSnapShot.bytesTransferred / taskSnapShot.totalByteCount
                        progressDialog.setMessage("Uploaded " + progress.toInt() + "%...")
                    }
        }
    }

    fun make_prediction(downu: Uri) {

        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)

        val hashMap:HashMap<Int,Array<String>> = HashMap<Int,Array<String>>()
        hashMap.put(0, arrayOf("aloo beans","5",day.toString()))
        hashMap.put(1,arrayOf("banana","8",day.toString()))
        hashMap.put(2,arrayOf("besan ladoo","17",day.toString()))
        hashMap.put(3,arrayOf("bhindi sabji","3",day.toString()))
        hashMap.put(4,arrayOf("chicken curry","6",day.toString()))
        hashMap.put(5,arrayOf("chole","12",day.toString()))

        val url = "http://e986272d.ngrok.io/?img=" + downu
        Log.d("url", url)
        val loginRequest = object: JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
            try {
                Toast.makeText(this,url,Toast.LENGTH_LONG).show()
                val code = response.getString("prediction").toInt()
                Log.d("Food", url)
                Log.d("Food", code.toString())
                Toast.makeText(this,code.toString(),Toast.LENGTH_LONG).show()

                for(key in hashMap.keys){
                    if(code == key) {
                        category = hashMap.get(key)!!
                        s = "true"
                    }
                }

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

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.data != null) {
            filepath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filepath)
                imageView!!.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"SELECT PICTURE"),PICK_IMAGE_REQUEST)

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






