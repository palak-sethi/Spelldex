package com.palaksethi.spelldex

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.palaksethi.spelldex.db.RemoteKeysDao
import com.palaksethi.spelldex.db.SpellDao
import com.palaksethi.spelldex.models.Data
import com.palaksethi.spelldex.models.SpellRemoteKeys
import com.palaksethi.spelldex.models.converters.SpellAttributeConverter

@Database(entities = [Data::class, SpellRemoteKeys::class], version = 1, exportSchema = false)
@TypeConverters(SpellAttributeConverter::class)
abstract class SpellDatabase : RoomDatabase() {

    abstract fun spellDao() : SpellDao
    abstract fun remoteKeysDao() : RemoteKeysDao

}