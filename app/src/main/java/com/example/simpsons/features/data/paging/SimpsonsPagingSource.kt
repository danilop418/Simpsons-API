package com.example.simpsons.features.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.simpsons.features.data.remote.SimpsonsApiService
import com.example.simpsons.features.data.remote.toModel
import com.example.simpsons.features.domain.Simpson
import retrofit2.HttpException
import java.io.IOException

class SimpsonsPagingSource(
    private val api: SimpsonsApiService
) : PagingSource<Int, Simpson>() {

    override fun getRefreshKey(state: PagingState<Int, Simpson>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Simpson> {
        val pageIndex = params.key ?: 1
        
        return try {
            val response = api.findAll(pageIndex)
            if (response.isSuccessful) {
                val apiResponse = response.body()
                val simpsons = apiResponse?.results?.map { it.toModel() } ?: emptyList()
                
                // Determine next key based on 'next' field existence
                val nextKey = if (apiResponse?.next != null) {
                    pageIndex + 1
                } else {
                    null
                }

                LoadResult.Page(
                    data = simpsons,
                    prevKey = if (pageIndex == 1) null else pageIndex - 1,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
