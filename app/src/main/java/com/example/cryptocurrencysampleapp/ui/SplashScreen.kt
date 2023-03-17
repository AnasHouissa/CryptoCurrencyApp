package com.example.cryptocurrencysampleapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cryptocurrencysampleapp.ui.activities.HomeActivity
import com.example.cryptocurrencysampleapp.ui.activities.LoginActivity
import com.example.cryptocurrencysampleapp.utils.Constants.REMEMBER_ME

import com.example.cryptocurrencysampleapp.utils.SharedPref

class SplashScreen : AppCompatActivity() {
    /*   val handler = Handler()
       handler.postDelayed({
           Toast.makeText(this, "Your message here", Toast.LENGTH_SHORT).show()
       }, 5000) // */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userPref = SharedPref(this)

        if (userPref.getBoolFrom(REMEMBER_ME)) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


    }
}