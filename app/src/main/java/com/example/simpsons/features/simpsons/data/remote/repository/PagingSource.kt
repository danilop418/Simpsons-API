package com.example.simpsons.features.simpsons.data.remote.repository

import androidx.paging.PagingState
import com.example.simpsons.features.simpsons.data.remote.api.SimpsonsApiService
import com.example.simpsons.features.simpsons.domain.Simpson
import java.io.IOException

class PagingSource(
    val service: SimpsonsApiService,
    val query: String
) : PagingSource<Int, Simpson>() {

    override suspend fun load(
        params: androidx.paging.PagingSource.LoadParams<Int>
    ): androidx.paging.PagingSource.LoadResult<Int, Simpson> {

        try {
        val nextPageNumber = params.key ?: 1
        val response = service.findAll(query, nextPageNumber)
        return androidx.paging.PagingSource.LoadResult.Page(
            data = response.simpsons,
            prevKey = null,
            nextKey = response.nextPageNumber
        )
    } catch (e: IOException) {
        return androidx.paging.PagingSource.LoadResult.Error(e)
    }
}
    override fun getRefreshKey(state: PagingState<Int, Simpson>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}