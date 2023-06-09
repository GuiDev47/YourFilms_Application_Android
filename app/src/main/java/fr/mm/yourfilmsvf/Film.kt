package fr.mm.yourfilmsvf

import com.google.gson.JsonObject

data class FilmList(
    val pages: Int,
    val results: List<Film>,
    val total_pages: Int,
    val total_results: Int,
)

data class Film(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

data class MovieResponse(
    val movie_results: List<MovieResult>,
    val person_results: List<Any>,
    val tv_results: List<Any>,
    val tv_episode_results: List<Any>,
    val tv_season_results: List<Any>
)

data class MovieResult(
    val adult: Boolean,
    val backdrop_path: String?,
    val id: Int,
    val title: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val poster_path: String?,
    val media_type: String,
    val genre_ids: List<Int>,
    val popularity: Double,
    val release_date: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)



data class IdInfos(
    val id: Int,
    val imdb_id: String,
    val wikidata_id: String,
    val facebook_id: String,
    val instagram_id: String,
    val twitter_id: String
)

fun convertToFilm(movieResult: MovieResult): Film {
    val (adult, backdrop_path, id, title, original_language, original_title, overview, poster_path, _, genre_ids, popularity, release_date, video, vote_average, vote_count) = movieResult

    return Film(
        adult,
        backdrop_path ?: "",
        genre_ids,
        id,
        original_language,
        original_title,
        overview,
        popularity,
        poster_path ?: "",
        release_date,
        title,
        video,
        vote_average,
        vote_count
    )
}






