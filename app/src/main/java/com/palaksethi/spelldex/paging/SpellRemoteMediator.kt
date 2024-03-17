package com.palaksethi.spelldex.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.palaksethi.spelldex.SpellDatabase
import com.palaksethi.spelldex.models.Data
import com.palaksethi.spelldex.models.SpellRemoteKeys
import com.palaksethi.spelldex.retrofit.SpellAPI

@ExperimentalPagingApi
class SpellRemoteMediator(
    private val spellAPI: SpellAPI,
    private val spellDatabase: SpellDatabase
) : RemoteMediator<Int, Data>() {

    val spellDao = spellDatabase.spellDao()
    val spellRemoteKeysDao = spellDatabase.remoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Data>): MediatorResult {
        return try{
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = spellAPI.getSpells(currentPage, 20)
            val endOfPaginationReached = response.meta?.pagination?.last == currentPage

            val prevPage = if(currentPage == 1) null else currentPage - 1
            val nextPage = if(endOfPaginationReached) null else currentPage + 1

            spellDatabase.withTransaction {
                spellDao.addSpells(response.data)
                val keys = response.data.map { spell ->
                    SpellRemoteKeys(
                        id = spell.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                spellRemoteKeysDao.addAllRemoteKeys(keys)
            }
            MediatorResult.Success(endOfPaginationReached)
        }
        catch(e: Exception){
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Data>
    ): SpellRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                spellRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Data>
    ): SpellRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { spell ->
                spellRemoteKeysDao.getRemoteKeys(id = spell.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Data>
    ): SpellRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { spell ->
                spellRemoteKeysDao.getRemoteKeys(id = spell.id)
            }
    }
}