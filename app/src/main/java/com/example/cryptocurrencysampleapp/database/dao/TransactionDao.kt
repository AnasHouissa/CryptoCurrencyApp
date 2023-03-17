package com.example.cryptocurrencysampleapp.database.dao

import androidx.room.*
import com.example.cryptocurrencysampleapp.models.Transaction

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun upsertTransaction (trans: Transaction): Long

    @Query("SELECT * FROM `Transaction`")
    suspend fun getTransactions(): List<Transaction>
}