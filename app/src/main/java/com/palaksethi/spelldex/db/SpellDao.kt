package com.palaksethi.spelldex.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.palaksethi.spelldex.models.Data

@Dao
interface SpellDao {
    @Query("SELECT * FROM spell")
    fun getSpells(): PagingSource<Int, Data>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSpells(spells: List<Data>)

    @Query("DELETE FROM spell")
    suspend fun deleteSpells()
}