package fr.mm.yourfilmsvf

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.text.Editable
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface MyService {

    companion object {
        const val API_KEY = "5ed2457caa404469f0efeafe37b654d8"
    }

    @GET("search/movie?api_key=$API_KEY&language=FR&page=1")
    fun getService(@Query("query") movie: String): Call<FilmList>

    @GET("movie/popular?api_key=$API_KEY&language=FR&page=1")
    fun getPopular(): Call<FilmList>

    @GET("recommendations?api_key=$API_KEY&language=FR&page=1")
    fun getRecommandations(): Call<FilmList>

    @Headers(
        "Content-Type: application/json",
        "Accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1ZWQyNDU3Y2FhNDA0NDY5ZjBlZmVhZmUzN2I2NTRkOCIsInN1YiI6IjY0N2NlYjlmMGUyOWEyMDBmOTgyNzYwYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.sVWjNnpsUQLInq08w6MiOYELeTWCRdJZp3exMgn4i00")
    @GET("account/19845726/favorite/movies?language=FR&page=1")
    fun getFavorites(): Call<FilmList>

    @Headers(
        "Content-Type: application/json",
        "Accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1ZWQyNDU3Y2FhNDA0NDY5ZjBlZmVhZmUzN2I2NTRkOCIsInN1YiI6IjY0N2NlYjlmMGUyOWEyMDBmOTgyNzYwYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.sVWjNnpsUQLInq08w6MiOYELeTWCRdJZp3exMgn4i00")
    @POST("account/19845726/favorite")
    fun addFavorites(@Body body: RequestBody): Call<JsonObject>

    @GET("movie/{scannedResult}/external_ids?api_key=$API_KEY")
    fun getIdFromExternalId(@Path("scannedResult") scannedResult: String): Call<IdInfos>

    @GET("find/{external_id}?api_key=$API_KEY&external_source=imdb_id&language=FR")
    fun getFilmById(@Path("external_id") externalId: String): Call<MovieResponse>

    @GET("movie/top_rated?api_key=$API_KEY&language=FR&page=1")
    fun getTopRated(): Call<FilmList>

    @GET("trending/movie/week?api_key=$API_KEY&language=FR")
    fun getTendanceSemaine(): Call<FilmList>

}