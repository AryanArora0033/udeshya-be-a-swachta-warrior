package com.example.udeshya

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        SharedPreferences.init(this)
        val isUserLoggedIn=SharedPreferences.getBoolean(PrefConstant.Is_User_Logged_In)
        if(isUserLoggedIn){

            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        else{
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
}