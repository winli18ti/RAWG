package com.winli.rawg.core.data.source.local

import com.winli.rawg.core.data.source.local.entity.GameEntity
import com.winli.rawg.core.data.source.local.room.GameDao

class LocalDataSource(private val gameDao: GameDao) {

    fun getFavoriteGames() = gameDao.getFavoriteGames()

    fun getGame(id: Int) = gameDao.getGame(id)

    suspend fun insertGames(games: List<GameEntity>) =
        gameDao.insertGames(games)

    fun setFavoriteGame(game: GameEntity, newState: Boolean) {
        game.isFavorite = newState
        gameDao.updateGame(game)
    }
}