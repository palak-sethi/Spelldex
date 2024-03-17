package com.palaksethi.spelldex.retrofit

import com.palaksethi.spelldex.models.Spells
import retrofit2.http.GET
import retrofit2.http.Query

interface SpellAPI {

    @GET("/v1/spells")
    suspend fun getSpells(@Query("page[number]") page: Int, @Query("page[limit]") limit: Int): Spells
}