package com.example.simpsons.features.simpsons.data.remote.repository

import androidx.paging.PagingState
import com.example.simpsons.features.simpsons.data.remote.api.SimpsonsApiService
import com.example.simpsons.features.simpsons.domain.Simpson

class PagingSource(val service: SimpsonsApiService,
                   val query: String) : PagingSource<Int, Simpson>() {

    override suspend fun load(
        params: androidx.paging.PagingSource.LoadParams<Int>
    ): androidx.paging.PagingSource.LoadResult<Int, Simpson> {

        override fun getRefreshKey(state: PagingState<Int, Simpson>): Int? {

            return state.anchorPosition?.let { anchorPosition ->
                val anchorPage = state.closestPageToPosition(anchorPosition)
                anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
            }
        }
    }