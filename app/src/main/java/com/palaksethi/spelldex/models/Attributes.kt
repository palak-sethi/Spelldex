package com.palaksethi.spelldex.models

import com.google.gson.annotations.SerializedName


data class Attributes(

    @SerializedName("slug") var slug: String? = null,
    @SerializedName("category") var category: String? = null,
    @SerializedName("creator") var creator: String? = null,
    @SerializedName("effect") var effect: String? = null,
    @SerializedName("hand") var hand: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("incantation") var incantation: String? = null,
    @SerializedName("light") var light: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("wiki") var wiki: String? = null

)