package com.winli.rawg.core.data

import com.winli.rawg.core.data.source.local.LocalDataSource
import com.winli.rawg.core.data.source.remote.RemoteDataSource
import com.winli.rawg.core.data.source.remote.network.ApiResponse
import com.winli.rawg.core.data.source.remote.response.GameResponse
import com.winli.rawg.core.domain.model.Game
import com.winli.rawg.core.domain.repository.IGameRepository
import com.winli.rawg.core.utils.DataMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class GameRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IGameRepository {

    override fun getGames(): Flow<List<Game>> =
        remoteDataSource.getGames().map {
            DataMapper.mapResponsesToDomain(it)
        }

    override fun getGames(search: String): Flow<List<Game>> =
        remoteDataSource.getGames(search).map {
            DataMapper.mapResponsesToDomain(it)
        }

    override fun getFavoriteGames(): Flow<List<Game>> =
        localDataSource.getFavoriteGames().map {
            DataMapper.mapEntitiesToDomain(it)
        }

    override fun getGame(id: Int): Flow<Resource<Game>> =
        object : NetworkBoundResource<Game, GameResponse>() {
            override fun loadFromDB(): Flow<Game> =
                localDataSource.getGame(id).map {
                    if (it != null) {
                        DataMapper.mapEntityToDomain(it)
                    } else {
                        it
                    }
                }

            override fun shouldFetch(data: Game?): Boolean =
                data == null

            override suspend fun createCall(): Flow<ApiResponse<GameResponse>> =
                remoteDataSource.getGame(id)

            override suspend fun saveCallResult(data: GameResponse) {
                val game = DataMapper.mapResponseToEntity(data)
                localDataSource.insertGames(arrayListOf(game))
            }
        }.asFlow()

    override fun setFavoriteGame(game: Game, state: Boolean) {
        val gameEntity = DataMapper.mapDomainToEntity(game)
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            localDataSource.setFavoriteGame(gameEntity, state)
        }
    }
}