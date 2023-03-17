package com.example.cryptocurrencysampleapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptocurrencysampleapp.models.User
import com.example.cryptocurrencysampleapp.utils.Constants.USER
import com.google.gson.Gson

class SharedPref(context: Context) {
    private val TAG = "SharedPref"

    private val applicationContext = context.applicationContext
    val sharedPreferences = applicationContext.getSharedPreferences(
        "userAuth",
        AppCompatActivity.MODE_PRIVATE
    )
    private val gson = Gson()


    fun insertBool(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }
    fun insertString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }
    fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    fun getBoolFrom(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun removeBool(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }
    fun saveUser(user: User) {
        val userJsonString = gson.toJson(user)
        sharedPreferences.edit().putString(USER, userJsonString).apply()
    }

    fun getUser(): User? {
        val userJsonString = sharedPreferences.getString(USER, null)
        return if (userJsonString != null) gson.fromJson(userJsonString, User::class.java) else null
    }

}