package com.flatstack.mytddapplication.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.flatstack.mytddapplication.R
import com.flatstack.mytddapplication.ui.search.MoviePagedListAdapter
import com.flatstack.mytddapplication.ui.search.SearchViewModel
import com.flatstack.mytddapplication.ui.util.GlideApp
import com.flatstack.mytddapplication.ui.util.provideViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModel: SearchViewModel by provideViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAdapter()
        viewModel.searchMovie("Matrix")
    }

    private fun initAdapter() {
        val glide = GlideApp.with(this)
        val adapter = MoviePagedListAdapter(glide, { viewModel.retry() },
            {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })
        viewModel.movies.observe(this, Observer {
            adapter.submitList(it)
        })
        viewModel.networkState.observe(this, Observer {
            adapter.setNetworkState(it)
        })
        rv_movies.layoutManager = LinearLayoutManager(this)
        rv_movies.adapter = adapter
    }
}
