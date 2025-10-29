package com.example.simpsons.features.simpsons.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.simpsons.features.simpsons.data.remote.api.SimpsonsApiService
import com.example.simpsons.features.simpsons.domain.Simpson
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class SimpsonsPagingSource(
    private val service: SimpsonsApiService,
    private val query: String
) : PagingSource<Int, Simpson>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Simpson> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = service.findById(query = query, page = page)

            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!

                LoadResult.Page(
                    data = body.results.map { apiModel ->
                        Simpson(
                            id = apiModel.id.toString(),
                            name = apiModel.name,
                            imageUrl = apiModel.portraitPath,
                            phrase = apiModel.phrases.firstOrNull() ?: ""
                        )
                    },
                    prevKey = if (body.prev == null) null else page - 1,
                    nextKey = if (body.next == null) null else page + 1
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Simpson>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}