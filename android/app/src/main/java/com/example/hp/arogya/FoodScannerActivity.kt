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

class FoodScannerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var filepath: Uri? = null
    private val PICK_IMAGE_REQUEST = 1234

    internal var storage: FirebaseStorage?=null
    internal var storageReference: StorageReference?=null
    private lateinit var mStorageRef:StorageReference

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

    }

    private fun uploadFile() {
        if(filepath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Loading...")
            progressDialog.show()

            val imageRef = storageReference!!.child("images/" + UUID.randomUUID().toString())
            imageRef.putFile(filepath!!)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext,"File Uploaded", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext,"failed", Toast.LENGTH_SHORT).show()
                    }
                    .addOnProgressListener { taskSnapShot ->
                        val progress = 100.0 * taskSnapShot.bytesTransferred/taskSnapShot.totalByteCount
                        progressDialog.setMessage("Uploaded "+progress.toInt()+"%...")
                    }
        }

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
