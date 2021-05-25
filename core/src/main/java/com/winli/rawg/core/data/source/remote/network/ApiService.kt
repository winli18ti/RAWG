package com.winli.rawg.core.data.source.remote.network

import com.winli.rawg.core.BuildConfig
import com.winli.rawg.core.data.source.remote.response.GameResponse
import com.winli.rawg.core.data.source.remote.response.GamesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    suspend fun getGames(
        @Query("key") key: String = API_KEY
    ): GamesResponse

    @GET("games")
    suspend fun getGames(
        @Query("search") search: String,
        @Query("key") key: String = API_KEY
    ): GamesResponse

    @GET("games/{id}")
    suspend fun getGame(
        @Path("id") id: Int,
        @Query("key") key: String = API_KEY
    ): GameResponse

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
    }
}