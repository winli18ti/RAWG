package com.winli.rawg.ui.home

import androidx.lifecycle.*
import com.winli.rawg.core.domain.usecase.GameUseCase
import com.winli.rawg.core.ui.model.GameView
import com.winli.rawg.core.utils.DataMapper

class HomeViewModel(private val gameUseCase: GameUseCase) : ViewModel() {

    private val search = MutableLiveData<String>()

    init {
        search.value = ""
    }

    private val games = Transformations.switchMap(search) { search ->
        if (search.isEmpty()) {
            gameUseCase.getGames().asLiveData()
        } else {
            gameUseCase.getGames(search).asLiveData()
        }
    }

    val gamesView: LiveData<List<GameView>> = Transformations.switchMap(games) { games ->
        MutableLiveData(
            DataMapper.mapDomainToViews(games)
        )
    }

    fun setSearch(search: String) {
        this.search.value = search
    }
}