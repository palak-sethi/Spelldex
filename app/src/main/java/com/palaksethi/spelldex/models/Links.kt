package com.palaksethi.spelldex.models

import com.google.gson.annotations.SerializedName


data class Links(

    @SerializedName("self") var self: String? = null,
    @SerializedName("current") var current: String? = null,
    @SerializedName("first") var first: String? = null,
    @SerializedName("prev") var prev: String? = null,
    @SerializedName("next") var next: String? = null,
    @SerializedName("last") var last: String? = null

)