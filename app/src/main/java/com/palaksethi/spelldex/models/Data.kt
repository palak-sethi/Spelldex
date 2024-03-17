package com.palaksethi.spelldex.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.palaksethi.spelldex.models.Attributes
import com.palaksethi.spelldex.models.Links
import com.google.gson.annotations.SerializedName
import com.palaksethi.spelldex.models.converters.SpellAttributeConverter

@Entity(tableName = "Spell")
data class Data(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id") var id: String,
    @TypeConverters(SpellAttributeConverter::class)
    @SerializedName("attributes") var attributes: Attributes? = Attributes()

)