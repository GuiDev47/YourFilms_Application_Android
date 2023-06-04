package fr.mm.yourfilmsvf

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.text.Editable
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MyService {

    companion object {
        const val API_KEY = "5ed2457caa404469f0efeafe37b654d8"
    }

    @GET("movie?api_key=$API_KEY&language=FR")
    fun getService(@Query("query") movie: String): Call<JsonElement>

    @GET("movie/popular?api_key=$API_KEY&page=1&language=FR")
    fun getPopular(): Call<JsonObject>
}