package com.flatstack.mytddapplication.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.flatstack.mytddapplication.R
import com.flatstack.mytddapplication.ui.util.GlideApp
import com.flatstack.mytddapplication.ui.util.provideViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

class SearchFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModel: SearchViewModel by provideViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(view) {
        initAdapter()
        btn_search.setOnClickListener {
            viewModel.searchMovie(et_search.text.toString())
        }
    }

    private fun initAdapter() {
        val glide = GlideApp.with(this)
        val adapter = MoviePagedListAdapter(glide, { viewModel.retry() },
            {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                val action = SearchFragmentDirections.actionSearchFragmentToMovieActivity().setMovieId(it)
                findNavController().navigate(action)
            })
        viewModel.movies.observe(this, Observer {
            adapter.submitList(it)
        })
        viewModel.networkState.observe(this, Observer {
            adapter.setNetworkState(it)
        })
        rv_movies.layoutManager = LinearLayoutManager(context)
        rv_movies.adapter = adapter
    }
}
