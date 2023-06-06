package fr.mm.yourfilmsvf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
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
import android.view.MenuItem
import android.widget.*


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)


        val popular = findViewById<ImageView>(R.id.image)
        val text = findViewById<TextView>(R.id.text)
        //Appel API
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/search/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val movieService = retrofit.create(MyService::class.java)
        val result = movieService.getService("star wars")
        result.enqueue(object : Callback<FilmList> {
            override fun onResponse(call: Call<FilmList>, response: Response<FilmList>) {
                if (response.isSuccessful) {
                    Log.d("HomeActivity", "Réponse réussie")
                    val filmList = response.body()
                    val dataList = filmList?.results?: emptyList()
                    val posterPath = dataList.get(0)?.poster_path
                    text.text=posterPath
                    Picasso.get()
                        .load("https://image.tmdb.org/t/p/w500$posterPath")
                        .into(popular)

                } else {
                    Log.d("API Error", "Response code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<FilmList>, t: Throwable) {
                Log.e("API Error", "API call failed", t)
            }
        })

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item_qrcode -> {
                    val fragment_home = fragment_home() // Remplacez FragmentMenu1 par votre propre fragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment_home)
                        .commit()
                    true
                }
                R.id.menu_item_main -> {
                    val fragment_home = fragment_home() // Remplacez FragmentMenu1 par votre propre fragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment_home)
                        .commit()
                    true
                }
                R.id.menu_item_heart -> {
                    val fragment_home = fragment_home() // Remplacez FragmentMenu1 par votre propre fragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment_home)
                        .commit()
                    true
                }
                else -> false
            }
        }

        }







}
