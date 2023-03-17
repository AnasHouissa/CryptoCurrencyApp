package com.example.cryptocurrencysampleapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Transaction(
    val recept: String,
    val expe: String,
    val amount: Int,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
) {
}