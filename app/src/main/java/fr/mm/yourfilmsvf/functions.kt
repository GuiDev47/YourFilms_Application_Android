package fr.mm.yourfilmsvf

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject

fun arrayToFilms(array:JsonArray): ArrayList<FilmClass>{
    val size = array.size()
    val films = ArrayList<FilmClass>()

    for (i in 0 until size){
        val film = array[i] as JsonObject
        val nom = film?.get("title")?.toString()
        val description = film.get("overview")?.toString()
        val id = film.get("id")?.toString()
        val poster_path = film.get("poster_path")?.toString()

        val temp = JsonObject()
        temp.addProperty("title", nom)
        temp.addProperty("overview", description)
        temp.addProperty("id", id)
        temp.addProperty("poster_path", poster_path)

        val gson = Gson()
        val filmClass: FilmClass = gson.fromJson(temp, FilmClass::class.java)

        films.add(filmClass)
    }

    return films

}