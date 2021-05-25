package com.winli.rawg.core.domain.usecase

import com.winli.rawg.core.data.Resource
import com.winli.rawg.core.domain.model.Game
import com.winli.rawg.core.domain.repository.IGameRepository
import kotlinx.coroutines.flow.Flow

class GameInteractor(private val gameRepository: IGameRepository) : GameUseCase {

    override fun getGames() = gameRepository.getGames()

    override fun getGames(search: String): Flow<List<Game>> =
        gameRepository.getGames(search)

    override fun getFavoriteGames() = gameRepository.getFavoriteGames()

    override fun getGame(id: Int): Flow<Resource<Game>> = gameRepository.getGame(id)

    override fun setFavoriteGame(game: Game, state: Boolean) =
        gameRepository.setFavoriteGame(game, state)
}