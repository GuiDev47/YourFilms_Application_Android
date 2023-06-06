package fr.mm.yourfilmsvf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import fr.mm.yourfilmsvf.arrayToFilms


class TrendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trend)




        val Film = findViewById<TextView>(R.id.Film)
        Film.text = "ok"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/search/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val movieService = retrofit.create(MyService::class.java)
        val result = movieService.getService("star wars")

        result.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    val jsonResponse = response.body()
                    val jsonObject = jsonResponse?.asJsonObject
                    val array = jsonObject?.getAsJsonArray("results")

                    if (array != null) {
                        val films = arrayToFilms(array)

                        val popular = findViewById<ImageView>(R.id.trendingMovieImage)
                        val posterPath = films?.get(1)?.poster_path
                        val posterPath2 = posterPath?.substring(1, posterPath.length - 1)
                        //val posterPath = "/wqnLdwVXoBjKibFRR5U3y0aDUhs.jpg"
                        Picasso.get()
                            .load("https://image.tmdb.org/t/p/w500$posterPath2")
                            .into(popular)

                        Film.text = "https://image.tmdb.org/t/p/w500$posterPath2"

                        //Film.text = films?.get(1)?.poster_path
                    } else {
                        Film.text = "Pas de résultats disponibles"
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Film.text = "Erreur de chargement"
            }
        })




    }
}