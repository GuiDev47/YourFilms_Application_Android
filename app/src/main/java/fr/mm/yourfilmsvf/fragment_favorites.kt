package fr.mm.yourfilmsvf

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import com.google.gson.JsonElement
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class fragment_favorites : Fragment() {

    private var dataList: List<Film> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val movieService = retrofit.create(MyService::class.java)
        val result = movieService.getFavorites()
        result.enqueue(object : Callback<FilmList> {
            override fun onResponse(call: Call<FilmList>, response: Response<FilmList>) {
                if (response.isSuccessful) {

                    val filmList = response.body()
                    dataList = filmList?.results?: emptyList()
                    val recycler = fragment_recycler.newInstance(dataList)

                    childFragmentManager.beginTransaction()
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

        return view
    }

    companion object {
        fun newInstance(): fragment_favorites {
            return fragment_favorites()
        }
    }


}