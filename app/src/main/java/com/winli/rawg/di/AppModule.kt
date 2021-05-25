package com.winli.rawg.di

import com.winli.rawg.core.domain.usecase.GameInteractor
import com.winli.rawg.core.domain.usecase.GameUseCase
import com.winli.rawg.ui.detail.DetailViewModel
import com.winli.rawg.ui.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<GameUseCase> { GameInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}