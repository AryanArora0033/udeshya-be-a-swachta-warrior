package com.example.udeshya

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.udeshya.R.id
import com.example.udeshya.R.id.log_out
import com.example.udeshya.R.id.sharing_points
import com.example.udeshya.R.layout

class ProfileActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(layout.activity_profile)
        val share=findViewById<ImageView>(sharing_points)
        val profile_name=findViewById<TextView>(id.tv_profile)
        SharedPreferences.init(this)
        val name:String?=SharedPreferences.getString(PrefConstant.Name_of_User)
       profile_name.text=name.toString()

        val layout_points=findViewById<TextView>(id.txt_points)
        SharedPreferences.init(this)
        val points = SharedPreferences.getInt(PrefConstant.Points_of_User)
        layout_points.text=points.toString()


        share.setOnClickListener{
            Toast.makeText(this,"Sharing points",Toast.LENGTH_SHORT).show()
            shareit(this,layout_points)
        }

        val logout=findViewById<Button>(log_out)
        logout.setOnClickListener {
            SharedPreferences.init(this)
            SharedPreferences.putBoolean(PrefConstant.Is_User_Logged_In,false)
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show()
        }


    }

    fun shareit(context: Context, layoutPoints: TextView?) {
        val points = layoutPoints?.text.toString()
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My swachta points 🪙are: $points \n Proud to be a Swachta Sangrami")
        val chooser = Intent.createChooser(shareIntent, "Share your points")

        context.startActivity(chooser) // Assuming you have access to a context object
    }



}