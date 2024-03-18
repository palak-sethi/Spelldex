package com.palaksethi.spelldex.models.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.palaksethi.spelldex.models.Attributes

class SpellAttributeConverter {
    @TypeConverter
    fun fromAttributes(attributes: Attributes?): String? {
        return Gson().toJson(attributes)
    }

    @TypeConverter
    fun toAttributes(attributesString: String?): Attributes? {
        return Gson().fromJson(attributesString, Attributes::class.java)
    }
}