package fr.mm.yourfilmsvf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.JsonElement
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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

                    // Utilisez Gson pour analyser le JSON
                    val gson = Gson()
                    val jsonObject = jsonResponse?.asJsonObject

                    // Traitez le contenu de jsonObject selon vos besoins
                    val jsonString = jsonResponse?.toString()
                    Film.text = jsonString
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                // Gérer l'erreur
            }
        })




    }
}