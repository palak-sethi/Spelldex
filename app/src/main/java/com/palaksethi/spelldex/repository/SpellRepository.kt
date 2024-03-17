package com.palaksethi.spelldex.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.palaksethi.spelldex.SpellDatabase
import com.palaksethi.spelldex.paging.SpellPagingSource
import com.palaksethi.spelldex.paging.SpellRemoteMediator
import com.palaksethi.spelldex.retrofit.SpellAPI
import javax.inject.Inject

@ExperimentalPagingApi
class SpellRepository @Inject constructor(
    private val spellAPI: SpellAPI,
    private val spellDatabase: SpellDatabase
) {

    fun getSpells() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        remoteMediator = SpellRemoteMediator(spellAPI, spellDatabase),
        pagingSourceFactory = { spellDatabase.spellDao().getSpells() }
    ).liveData
}