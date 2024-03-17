package com.palaksethi.spelldex.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SpellRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)
