package com.example.udeshya

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.example.udeshya.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    val code=450


    val permissionarray= arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.INTERNET,
        Manifest.permission.CAMERA


        )
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //asking for permissions
        askforpermission()

        //binding initialisation
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //default Fragment that is home fragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, HomeFragment.newInstance()) // Replace DefaultFragment with the fragment you want to display by default
            commit()
        }

        //setting clicklisten on the bottom navigation icons to open fragments
        binding.bottomNavbar.menu.forEach { it.isChecked = false }

       binding.bottomNavbar.setOnItemSelectedListener {
           if(it.itemId==R.id.location){
               inflatefragment(MapsFragment())
               true

           }
           else if (it.itemId==R.id.camera){
//               inflatefragment(CameraFragment.newInstance())
//               true
               val intent=Intent(this,CameraActivity::class.java)
               startActivity(intent)
               true
           }
           else if(it.itemId==R.id.leaderboard){
               inflatefragment(LeaderboardFragment.newInstance())
               true
           }
           else false



       }






    }



    private fun askforpermission() {
        ActivityCompat.requestPermissions(this,permissionarray,code)
    }

    private fun inflatefragment(newinstance : Fragment) {
         val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,newinstance)
        transaction.commit()
    }



}