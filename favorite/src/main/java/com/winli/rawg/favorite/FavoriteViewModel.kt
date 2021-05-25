package com.winli.rawg.favorite

import androidx.lifecycle.*
import com.winli.rawg.core.domain.model.Game
import com.winli.rawg.core.domain.usecase.GameUseCase
import com.winli.rawg.core.ui.model.GameView
import com.winli.rawg.core.utils.DataMapper

class FavoriteViewModel(private val gameUseCase: GameUseCase) : ViewModel() {

    private val favoriteGames = gameUseCase.getFavoriteGames().asLiveData()

    val gamesView: LiveData<List<GameView>> =
        Transformations.switchMap(favoriteGames) { favoriteGames ->
            MutableLiveData(
                DataMapper.mapDomainToViews(favoriteGames)
            )
        }

    fun setFavoriteGame(game: Game) = gameUseCase.setFavoriteGame(game, !game.isFavorite)

    fun getGame(gameView: GameView) = favoriteGames.value?.find { it.id == gameView.id } as Game
}