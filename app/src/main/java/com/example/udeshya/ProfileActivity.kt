package com.example.udeshya

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContentView(R.layout.activity_profile)
        val layout_points=findViewById<TextView>(R.id.txt_points)
        SharedPreferences.init(this)
        val points = SharedPreferences.getInt(PrefConstant.Points_of_User)
        layout_points.text=points.toString()


    }
}