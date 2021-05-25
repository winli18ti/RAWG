package com.winli.rawg.core.domain.usecase

import com.winli.rawg.core.data.Resource
import com.winli.rawg.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GameUseCase {
    fun getGames(): Flow<List<Game>>
    fun getGames(search: String): Flow<List<Game>>
    fun getFavoriteGames(): Flow<List<Game>>
    fun getGame(id: Int): Flow<Resource<Game>>
    fun setFavoriteGame(game: Game, state: Boolean)
}