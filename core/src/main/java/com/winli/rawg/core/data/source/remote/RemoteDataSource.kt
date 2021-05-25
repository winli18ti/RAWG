package com.winli.rawg.core.data.source.remote

import com.winli.rawg.core.data.source.remote.network.ApiResponse
import com.winli.rawg.core.data.source.remote.network.ApiService
import com.winli.rawg.core.data.source.remote.response.GameResponse
import com.winli.rawg.core.data.source.remote.response.ResultsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class RemoteDataSource(private val apiService: ApiService) {

    fun getGames(): Flow<List<ResultsItem>> =
        flow {
            try {
                val dataArray = apiService.getGames().results
                emit(dataArray)
            } catch (e: Exception) {
                Timber.e(e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun getGames(search: String): Flow<List<ResultsItem>> =
        flow {
            try {
                val dataArray = apiService.getGames(search = search).results
                emit(dataArray)
            } catch (e: Exception) {
                Timber.e(e.toString())
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getGame(id: Int): Flow<ApiResponse<GameResponse>> =
        flow {
            try {
                val response = apiService.getGame(id)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Timber.e(e.toString())
            }
        }.flowOn(Dispatchers.IO)
}