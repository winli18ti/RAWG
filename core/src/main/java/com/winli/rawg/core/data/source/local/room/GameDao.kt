package com.winli.rawg.core.data.source.local.room

import androidx.room.*
import com.winli.rawg.core.data.source.local.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Query("SELECT * FROM game WHERE isFavorite = 1")
    fun getFavoriteGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM game WHERE id=:id")
    fun getGame(id: Int): Flow<GameEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGames(games: List<GameEntity>)

    @Update
    fun updateGame(game: GameEntity)
}