package fr.mm.yourfilmsvf

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonElement
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class fragment_search : Fragment() {

    private var dataList: List<Film> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val button = view.findViewById<Button>(R.id.searchButton)
        button.setOnClickListener {
            val context: Context = requireContext()
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val currentFocusedView = requireActivity().currentFocus
            inputMethodManager.hideSoftInputFromWindow(currentFocusedView?.windowToken, 0)
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val movieService = retrofit.create(MyService::class.java)
        val result = movieService.getPopular()
        result.enqueue(object : Callback<FilmList> {
            override fun onResponse(call: Call<FilmList>, response: Response<FilmList>) {
                if (response.isSuccessful) {
                    val filmList = response.body()
                    dataList = filmList?.results?: emptyList()
                    val adapter = FilmAdapter_search(dataList)
                    recyclerView.adapter = adapter

                } else {
                    Log.d("API Error", "Response code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<FilmList>, t: Throwable) {
                Log.e("API Error", "API call failed", t)
            }
        })

        val search = view.findViewById<EditText>(R.id.searchEditText)
        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?){
                val text = s.toString()

                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val movieService = retrofit.create(MyService::class.java)
                val result = movieService.getService(text)
                result.enqueue(object : Callback<FilmList> {
                    override fun onResponse(call: Call<FilmList>, response: Response<FilmList>) {
                        if (response.isSuccessful) {
                            val filmList = response.body()
                            dataList = filmList?.results?: emptyList()
                        } else {
                            Log.d("API Error", "Response code: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<FilmList>, t: Throwable) {
                        Log.e("API Error", "API call failed", t)
                    }
                })

                val adapter = FilmAdapter_search(dataList)
                recyclerView.adapter = adapter
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        return view
    }

    companion object {
        fun newInstance(): fragment_search {
            return fragment_search()
        }
    }


}