package com.flatstack.mytddapplication.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flatstack.mytddapplication.R
import com.flatstack.mytddapplication.entities.SearchResult
import com.flatstack.mytddapplication.ui.util.GlideRequests
import kotlinx.android.synthetic.main.item_movie_search.view.*

class MovieViewHolder(
    itemView: View,
    private var glide: GlideRequests
) : RecyclerView.ViewHolder(itemView) {

    fun bind(movie: SearchResult.Movie?, onClickCallback: (String) -> Unit) = with(itemView) {
        movie?.let { movie ->
            glide.clear(iv_thumbnail)
            tv_title.text = movie.title
            tv_type.text = movie.type?.name?.toUpperCase() ?: ""
            tv_year.text = movie.year
            setOnClickListener { onClickCallback(movie.imdbID) }
            glide.load(movie.poster)
                .fitCenter()
                .into(iv_thumbnail)
        }
    }

    companion object {
        fun create(parent: ViewGroup, glide: GlideRequests) = MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_search, parent, false),
            glide
        )
    }
}
