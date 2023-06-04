package fr.mm.yourfilmsvf

import com.google.gson.JsonObject

data class Film(
    var page : Int,
    var results : Array<FilmJson>,
)

data class FilmJson(
    var original_title : String,
    var overview : String
)
