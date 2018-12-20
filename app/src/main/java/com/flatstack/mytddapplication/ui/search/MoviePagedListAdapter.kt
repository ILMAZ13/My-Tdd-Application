package com.flatstack.mytddapplication.ui.search

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.flatstack.mytddapplication.R
import com.flatstack.mytddapplication.entities.NetworkState
import com.flatstack.mytddapplication.entities.SearchResult
import com.flatstack.mytddapplication.ui.util.GlideRequests

class MoviePagedListAdapter(
    private val glide: GlideRequests,
    private val retryCallback: () -> Unit,
    private val onItemClickCallback: (String) -> Unit
) : PagedListAdapter<SearchResult.Movie, RecyclerView.ViewHolder>(MOVIE_COMPARATOR) {
    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.item_network_state -> NetworkStateViewHolder.create(parent, retryCallback)
            R.layout.item_movie_search -> MovieViewHolder.create(parent, glide)
            else -> throw IllegalStateException("unknown view type $viewType")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> holder.bind(getItem(position), onItemClickCallback)
            is NetworkStateViewHolder -> holder.bind(networkState)
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            R.layout.item_movie_search
        }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    companion object {
        val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<SearchResult.Movie>() {
            override fun areItemsTheSame(oldItem: SearchResult.Movie, newItem: SearchResult.Movie): Boolean {
                return oldItem.imdbID == newItem.imdbID
            }

            override fun areContentsTheSame(oldItem: SearchResult.Movie, newItem: SearchResult.Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}
