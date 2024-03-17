package com.palaksethi.spelldex.models

import com.google.gson.annotations.SerializedName


data class Meta(

    @SerializedName("pagination") var pagination: Pagination? = Pagination(),
    @SerializedName("copyright") var copyright: String? = null,
    @SerializedName("generated_at") var generatedAt: String? = null

)