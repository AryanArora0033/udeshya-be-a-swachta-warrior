package com.example.udeshya

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView


class SendLocation : AppCompatActivity() {
    val director_id="sandeepmishra69@gmail.com"
    val collector_id=" tripathimk2000@gmail.com"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_send_location)

        val send_location_coordinates=findViewById<ImageView>(R.id.send_Director)
        send_location_coordinates.setOnClickListener {
            val lati:String?=SharedPreferences.getString(PrefConstant.lati)
            val longi:String?=SharedPreferences.getString(PrefConstant.longi)
            Toast.makeText(this, "Sending garbage location $lati", Toast.LENGTH_SHORT).show()
            share1(lati,longi,director_id)
        }
        val send_location_coordinates2=findViewById<ImageView>(R.id.send_waste_collector)
        send_location_coordinates2.setOnClickListener {
            val lati:String?=SharedPreferences.getString(PrefConstant.lati)
            val longi:String?=SharedPreferences.getString(PrefConstant.longi)
            Toast.makeText(this, "Sending garbage location", Toast.LENGTH_SHORT).show()

            share2(lati,longi,collector_id)
        }


    }




    private fun share1(lati: String?, longi: String?, DirectorId: String) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(DirectorId))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "My Location Coordinates")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Latitude: $lati\nLongitude: $longi")

        // Verify that the device has an email app to handle the intent
        if (emailIntent.resolveActivity(packageManager) != null) {
            startActivity(emailIntent)
        }
    }

    private fun share2(lati: String?, longi: String?, collectorId: String) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(collectorId))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "My Location Coordinates")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Latitude: $lati\nLongitude: $longi")

        // Verify that the device has an email app to handle the intent
        if (emailIntent.resolveActivity(packageManager) != null) {
            startActivity(emailIntent)
        }
    }


}
