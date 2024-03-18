package com.palaksethi.spelldex.retrofit

import com.palaksethi.spelldex.models.Data
import com.palaksethi.spelldex.models.SpellItem
import com.palaksethi.spelldex.models.Spells
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GetData {

    @GET("/v1/spells/{spellId}")
    fun getSpellById(@Path("spellId") spellId : String): Call<SpellItem>


}