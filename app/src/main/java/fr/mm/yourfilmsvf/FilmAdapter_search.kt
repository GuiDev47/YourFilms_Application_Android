package fr.mm.yourfilmsvf

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class FilmAdapter_search(private val dataList: List<Film>) : RecyclerView.Adapter<FilmAdapter_search.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById<TextView>(R.id.title)
        val poster: ImageView = view.findViewById<ImageView>(R.id.poster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.film_element_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList.get(position)
        holder.title.text = data.title
        val posterPath=data.poster_path

        Picasso.get()
            .load("https://image.tmdb.org/t/p/w500$posterPath")
            .into(holder.poster)

        val popular = holder.poster.setOnClickListener{
            val context = holder.itemView.context
            val intent = Intent(context, activity_film_details::class.java)
            val gson = Gson()
            val filmString = gson.toJson(data)
            intent.putExtra("film", filmString)
            context.startActivity(intent)
        }
    }




    override fun getItemCount(): Int {
        return dataList.size
    }

}