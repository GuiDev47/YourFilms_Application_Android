package fr.mm.yourfilmsvf

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class fragment_recycler : Fragment() {


    private var dataList: List<Film> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recycler, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        val adapter = FilmAdapter(dataList)
        recyclerView.adapter = adapter

        return view
    }

    companion object {
        fun newInstance(dataList: List<Film>): fragment_recycler {
            val fragment = fragment_recycler()
            fragment.dataList = dataList
            return fragment
        }
    }


}