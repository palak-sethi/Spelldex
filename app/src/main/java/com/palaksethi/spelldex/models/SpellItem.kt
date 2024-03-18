package com.palaksethi.spelldex.models

import com.google.gson.annotations.SerializedName
import com.palaksethi.spelldex.models.Data

data class SpellItem(

    @SerializedName("data") var data: Data = Data(""),
    @SerializedName("meta") var meta: Meta? = Meta(),
    @SerializedName("links") var links: Links? = Links()

)