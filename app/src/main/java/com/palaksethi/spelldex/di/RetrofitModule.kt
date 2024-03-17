package com.palaksethi.spelldex.di

import com.palaksethi.spelldex.retrofit.SpellAPI
import com.palaksethi.spelldex.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun getSpellAPI(retrofit: Retrofit): SpellAPI {
        return retrofit.create(SpellAPI::class.java)
    }

}