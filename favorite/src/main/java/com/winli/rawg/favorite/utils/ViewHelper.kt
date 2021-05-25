package com.winli.rawg.favorite.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.winli.rawg.favorite.FavoriteAdapter

object ViewHelper {
    fun RecyclerView.setConfig(favoriteAdapter: FavoriteAdapter) {
        layoutManager = LinearLayoutManager(context)
        setHasFixedSize(true)
        adapter = favoriteAdapter
    }
}