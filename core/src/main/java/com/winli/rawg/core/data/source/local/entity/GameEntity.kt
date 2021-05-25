package com.winli.rawg.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "game")
data class GameEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "metacritic")
    var metacritic: Int,

    @ColumnInfo(name = "released")
    var released: String,

    @ColumnInfo(name = "backgroundImage")
    var backgroundImage: String,

    @ColumnInfo(name = "website")
    var website: String,

    @ColumnInfo(name = "rating")
    var rating: Double,

    @ColumnInfo(name = "playtime")
    var playtime: Int,

    @ColumnInfo(name = "ratingsCount")
    var ratingsCount: Int,

    @ColumnInfo(name = "platforms")
    var platforms: String,

    @ColumnInfo(name = "stores")
    var stores: String,

    @ColumnInfo(name = "developers")
    var developers: String,

    @ColumnInfo(name = "genres")
    var genres: String,

    @ColumnInfo(name = "tags")
    var tags: String,

    @ColumnInfo(name = "publishers")
    var publishers: String,

    @ColumnInfo(name = "esrbRating")
    var esrbRating: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
) : Parcelable