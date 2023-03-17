package com.example.cryptocurrencysampleapp.database.dao

import androidx.room.*
import com.example.cryptocurrencysampleapp.models.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun upsertUser (user: User): Long

    @Query("Select * from User where email = :email and pwd = :pwd")
    suspend fun getUser(email:String,pwd:String): User?

    @Query("Select * from User where id = :id")
    suspend fun getUserByID(id:Long): User?
}