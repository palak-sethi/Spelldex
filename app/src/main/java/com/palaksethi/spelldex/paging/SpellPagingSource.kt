package com.palaksethi.spelldex.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.palaksethi.spelldex.models.Data
import com.palaksethi.spelldex.retrofit.SpellAPI

class SpellPagingSource(private val spellAPI: SpellAPI) : PagingSource<Int, Data>() {
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            val position = params.key ?: 1
            val response = spellAPI.getSpells(position, 20)

            return LoadResult.Page(
                data = response.data,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.meta?.pagination?.last) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}