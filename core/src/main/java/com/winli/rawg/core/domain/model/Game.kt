package com.winli.rawg.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    var id: Int,
    var name: String,
    var description: String,
    var metacritic: Int,
    var released: String,
    var backgroundImage: String,
    var website: String,
    var rating: Double,
    var playtime: Int,
    var ratingsCount: Int,
    var platforms: String,
    var stores: String,
    var developers: String,
    var genres: String,
    var tags: String,
    var publishers: String,
    var esrbRating: String,
    var isFavorite: Boolean
) : Parcelable