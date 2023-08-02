package com.m4nth4n.budgettracker

import androidx.room.*

@Dao
interface transactionDao {
    @Query("SELECT * FROM transactions")
    fun getAll(): List<Transaction>

    @Insert
    fun insertAll(vararg transaction: Transaction)

    @Delete
    fun delete(transaction: Transaction)

    @Update
    fun update(vararg transaction: Transaction)

}