package fr.mm.yourfilmsvf

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class activity_film_details : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_details)

        var dataList: List<Film> = emptyList()

        val poster = findViewById<ImageView>(R.id.PosterFilm)
        val backdrop = findViewById<ImageView>(R.id.ImageFilm)
        val title = findViewById<TextView>(R.id.TitreFilm)
        val note = findViewById<TextView>(R.id.Note)
        val description = findViewById<TextView>(R.id.Description)
        val sortie = findViewById<TextView>(R.id.Sortie)

        val fav = findViewById<ImageView>(R.id.fav)
        val add = findViewById<ImageView>(R.id.add)

        val filmString: String? = intent.getStringExtra("film")
        if (filmString != null) {
            val gson = Gson()
            val film: Film = gson.fromJson(filmString, Film::class.java)

            title.text = film.title
            description.text = film.overview
            note.text = "Note du public : ${film.vote_average.toString()}/10"
            sortie.text = "Sortie le : ${film.release_date}"
            val id = film.id

            fav.setOnClickListener{
                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val movieService = retrofit.create(MyService::class.java)

                val body = "{\"media_type\":\"movie\",\"media_id\":${id},\"favorite\":true}"
                val mediaType = "application/json".toMediaType()
                val requestBody = RequestBody.create(mediaType, body)

                val result = movieService.addFavorites(requestBody)

                result.enqueue(object : Callback<JsonObject> {
                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        if (response.isSuccessful) {
                            Log.d("fav: ", "ok")
                            fav.setImageResource(R.drawable.heart)
                        } else {
                            Log.d("API Error", "Response code: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Log.e("API Error", "API call failed", t)
                    }
                })
            }

            Picasso.get()
                .load("https://image.tmdb.org/t/p/w500${film.poster_path}")
                .into(poster)

            Picasso.get()
                .load("https://image.tmdb.org/t/p/w500${film.backdrop_path}")
                .into(backdrop)

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/${film.id}/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val movieService = retrofit.create(MyService::class.java)
            val result = movieService.getRecommandations()
            result.enqueue(object : Callback<FilmList> {
                override fun onResponse(call: Call<FilmList>, response: Response<FilmList>) {
                    if (response.isSuccessful) {
                        val filmList = response.body()
                        dataList = filmList?.results?: emptyList()
                        val recycler = fragment_recycler.newInstance(dataList)

                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_recycler, recycler)
                            .commit()
                    } else {
                        Log.d("API Error", "Response code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<FilmList>, t: Throwable) {
                    Log.e("API Error", "API call failed", t)
                }
            })
        }





    }
}