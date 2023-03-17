package com.example.cryptocurrencysampleapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User (
    val email:String,
    val pwd:String,
    val fullname:String,
    var wallet:Int,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
        ){

}