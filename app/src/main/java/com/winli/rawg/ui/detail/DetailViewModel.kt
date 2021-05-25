package com.winli.rawg.ui.detail

import androidx.lifecycle.*
import com.winli.rawg.core.data.Resource
import com.winli.rawg.core.domain.model.Game
import com.winli.rawg.core.domain.usecase.GameUseCase
import com.winli.rawg.core.ui.model.GameDetailView
import com.winli.rawg.core.utils.DataMapper

class DetailViewModel(private val gameUseCase: GameUseCase) : ViewModel() {

    private val id = MutableLiveData<Int>()

    private val game = Transformations.switchMap(id) {
        gameUseCase.getGame(it).asLiveData()
    }

    val gameDetailView: LiveData<Resource<GameDetailView>> =
        Transformations.switchMap(game) { game ->
            val gameDetailView = MutableLiveData<Resource<GameDetailView>>()
            when (game) {
                is Resource.Loading -> {
                    gameDetailView.value = Resource.Loading()
                }
                is Resource.Success -> {
                    gameDetailView.value = Resource.Success(
                        DataMapper.mapDomainToViewDetail(game.data as Game)
                    )
                }
                is Resource.Error -> {
                    gameDetailView.value = Resource.Error(game.message.toString())
                }
            }
            gameDetailView
        }

    fun setId(id: Int) {
        this.id.value = id
    }

    fun setFavoriteGame(newState: Boolean) {
        gameUseCase.setFavoriteGame(game.value?.data as Game, newState)
    }
}