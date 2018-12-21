package com.flatstack.mytddapplication.ui.movie

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.flatstack.mytddapplication.R
import com.flatstack.mytddapplication.entities.Status
import com.flatstack.mytddapplication.ui.util.provideViewModel
import kotlinx.android.synthetic.main.activity_movie.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class MovieActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModel: MovieViewModel by provideViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.movie.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> pb_progress.visibility = View.VISIBLE
                Status.SUCCESS -> pb_progress.visibility = View.GONE
                Status.ERROR -> {
                    pb_progress.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
            it.data?.let { movie ->
                tv_title.text = movie.title
                tv_plot.text = movie.plot
            }
        })

        val id = MovieActivityArgs.fromBundle(intent.extras).movieId
        viewModel.getMovie(id)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
