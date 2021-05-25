package com.winli.rawg.core.utils

import com.winli.rawg.core.data.source.local.entity.GameEntity
import com.winli.rawg.core.data.source.remote.response.*
import com.winli.rawg.core.domain.model.Game
import com.winli.rawg.core.ui.model.GameDetailView
import com.winli.rawg.core.ui.model.GameView

object DataMapper {

    fun mapResponseToEntity(input: GameResponse) = GameEntity(
        id = input.id,
        name = input.name.toText(),
        description = input.descriptionRaw.toText(),
        metacritic = input.metacritic.toIntIfNull(),
        released = input.released.toText(),
        backgroundImage = input.backgroundImage.toText(),
        website = input.website.toText(),
        rating = input.rating.toDoubleIfNull(),
        playtime = input.playtime.toIntIfNull(),
        ratingsCount = input.ratingsCount.toIntIfNull(),
        platforms = input.platforms.toPlatformsText(),
        stores = input.stores.toStoresText(),
        developers = input.developers.toDevelopersText(),
        genres = input.genres.toGenresText(),
        tags = input.tags.toTagsText(),
        publishers = input.publishers.toPublishersText(),
        esrbRating = input.esrbRating.toEsrbRatingText()
    )

    fun mapResponsesToDomain(input: List<ResultsItem>): List<Game> =
        input.map {
            Game(
                id = it.id,
                name = it.name.toText(),
                description = "",
                metacritic = it.metacritic.toIntIfNull(),
                released = it.released.toText(),
                backgroundImage = it.backgroundImage.toText(),
                website = "",
                rating = it.rating.toDoubleIfNull(),
                playtime = it.playtime.toIntIfNull(),
                ratingsCount = it.ratingsCount.toIntIfNull(),
                platforms = it.platforms.toPlatformsText(),
                stores = it.stores.toStoresText(),
                developers = "",
                genres = it.genres.toGenresText(),
                tags = it.tags.toTagsText(),
                publishers = "",
                esrbRating = it.esrbRating.toEsrbRatingText(),
                isFavorite = false
            )
        }

    fun mapEntitiesToDomain(input: List<GameEntity>): List<Game> =
        input.map {
            mapEntityToDomain(it)
        }

    fun mapEntityToDomain(input: GameEntity) = Game(
        id = input.id,
        name = input.name,
        description = input.description,
        metacritic = input.metacritic,
        released = input.released,
        backgroundImage = input.backgroundImage,
        website = input.website,
        rating = input.rating,
        playtime = input.playtime,
        ratingsCount = input.ratingsCount,
        platforms = input.platforms,
        stores = input.stores,
        developers = input.developers,
        genres = input.genres,
        tags = input.tags,
        publishers = input.publishers,
        esrbRating = input.esrbRating,
        isFavorite = input.isFavorite
    )

    fun mapDomainToEntity(input: Game) = GameEntity(
        id = input.id,
        name = input.name,
        description = input.description,
        metacritic = input.metacritic,
        released = input.released,
        backgroundImage = input.backgroundImage,
        website = input.website,
        rating = input.rating,
        playtime = input.playtime,
        ratingsCount = input.ratingsCount,
        platforms = input.platforms,
        stores = input.stores,
        developers = input.developers,
        genres = input.genres,
        tags = input.tags,
        publishers = input.publishers,
        esrbRating = input.esrbRating,
        isFavorite = input.isFavorite
    )

    fun mapDomainToViews(input: List<Game>): List<GameView> =
        input.map {
            mapDomainToView(it)
        }

    private fun mapDomainToView(input: Game) = GameView(
        id = input.id,
        name = input.name,
        backgroundImage = input.backgroundImage,
        released = input.released
    )

    fun mapDomainToViewDetail(input: Game) = GameDetailView(
        id = input.id,
        name = input.name,
        description = input.description,
        metacritic = input.metacritic,
        released = input.released,
        backgroundImage = input.backgroundImage,
        website = input.website,
        rating = input.rating,
        playtime = input.playtime,
        ratingsCount = input.ratingsCount,
        platforms = input.platforms,
        stores = input.stores,
        developers = input.developers,
        genres = input.genres,
        tags = input.tags,
        publishers = input.publishers,
        esrbRating = input.esrbRating,
        isFavorite = input.isFavorite
    )

    private fun List<PlatformsItem>?.toPlatformsText(): String =
        this?.joinToString { it.platform.name } ?: ""


    private fun List<StoresItem>?.toStoresText(): String =
        this?.joinToString { it.store.name } ?: ""

    private fun List<DevelopersItem>?.toDevelopersText(): String =
        this?.joinToString { it.name } ?: ""

    private fun List<GenresItem>?.toGenresText(): String =
        this?.joinToString { it.name } ?: ""

    private fun List<TagsItem>?.toTagsText(): String =
        this?.joinToString { it.name } ?: ""

    private fun List<PublishersItem>?.toPublishersText(): String =
        this?.joinToString { it.name } ?: ""

    private fun EsrbRating?.toEsrbRatingText(): String =
        this?.name ?: ""

    private fun Int?.toIntIfNull(): Int =
        this ?: -1

    private fun Double?.toDoubleIfNull(): Double =
        this ?: -1.0

    private fun String?.toText(): String =
        this ?: ""
}