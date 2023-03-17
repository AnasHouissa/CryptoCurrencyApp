package com.example.cryptocurrencysampleapp.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptocurrencysampleapp.database.dao.TransactionDao
import com.example.cryptocurrencysampleapp.database.dao.UserDao
import com.example.cryptocurrencysampleapp.models.Transaction
import com.example.cryptocurrencysampleapp.models.User


@androidx.room.Database(entities = [User::class,Transaction::class], version = 2, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getTransactionDao(): TransactionDao

    companion object {
        @Volatile
        private var instance: Database? = null

        fun getDatabase(context: Context): Database {
            if (instance == null) {
                synchronized(this) {
                    instance = createDatabase(context)
                }
            }
            return instance!!
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, Database::class.java, "crypto.db")
                .build()
    }
}