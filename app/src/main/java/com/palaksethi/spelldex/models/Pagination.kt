package com.palaksethi.spelldex.models

import com.google.gson.annotations.SerializedName


data class Pagination(

    @SerializedName("current") var current: Int? = null,
    @SerializedName("first") var first: Int? = null,
    @SerializedName("prev") var prev: Int? = null,
    @SerializedName("next") var next: Int? = null,
    @SerializedName("last") var last: Int? = null,
    @SerializedName("records") var records: Int? = null

)