package fr.mm.yourfilmsvf

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FilmAdapter(private val dataList: List<Film>) : RecyclerView.Adapter<FilmAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById<TextView>(R.id.title)
        val poster: ImageView = view.findViewById<ImageView>(R.id.poster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.film_element, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList.get(position)
        holder.title.text = data.title
        val posterPath=data.poster_path

        Picasso.get()
            .load("https://image.tmdb.org/t/p/w500$posterPath")
            .into(holder.poster)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}