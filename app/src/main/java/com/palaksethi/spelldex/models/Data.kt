package com.palaksethi.spelldex.models

import com.palaksethi.spelldex.models.Attributes
import com.palaksethi.spelldex.models.Links
import com.google.gson.annotations.SerializedName

data class Data(

    @SerializedName("id") var id: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("attributes") var attributes: Attributes? = Attributes(),
    @SerializedName("links") var links: Links? = Links()

)