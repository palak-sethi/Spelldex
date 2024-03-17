package com.palaksethi.spelldex.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.palaksethi.spelldex.models.SpellRemoteKeys

@Dao
interface RemoteKeysDao {
    @Query("SELECT * FROM SpellRemoteKeys WHERE id =:id")
    suspend fun getRemoteKeys(id: String?): SpellRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<SpellRemoteKeys>)

    @Query("DELETE FROM SpellRemoteKeys")
    suspend fun deleteAllRemoteKeys()
}