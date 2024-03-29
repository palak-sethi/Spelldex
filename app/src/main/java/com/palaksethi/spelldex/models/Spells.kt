package com.palaksethi.spelldex.models

import com.google.gson.annotations.SerializedName

data class Spells(

    @SerializedName("data") var data: ArrayList<Data> = arrayListOf(),
    @SerializedName("meta") var meta: Meta? = Meta(),
    @SerializedName("links") var links: Links? = Links()

)