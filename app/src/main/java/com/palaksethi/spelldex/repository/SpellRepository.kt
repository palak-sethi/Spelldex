package com.palaksethi.spelldex.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.palaksethi.spelldex.paging.SpellPagingSource
import com.palaksethi.spelldex.retrofit.SpellAPI
import javax.inject.Inject

class SpellRepository @Inject constructor(private val spellAPI: SpellAPI) {

    fun getSpells() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { SpellPagingSource(spellAPI) }
    ).liveData
}