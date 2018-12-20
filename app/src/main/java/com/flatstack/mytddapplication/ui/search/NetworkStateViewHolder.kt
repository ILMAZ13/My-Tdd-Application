package com.flatstack.mytddapplication.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flatstack.mytddapplication.R
import com.flatstack.mytddapplication.entities.NetworkState
import com.flatstack.mytddapplication.entities.Status
import kotlinx.android.synthetic.main.item_network_state.view.*

class NetworkStateViewHolder(
    itemView: View,
    private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.btn_retry.setOnClickListener { retryCallback() }
    }

    fun bind(networkState: NetworkState?) = with(itemView) {
        progress_bar.visibility = toVisibility(networkState?.status == Status.LOADING)
        btn_retry.visibility = toVisibility(networkState?.status == Status.ERROR)
        tv_error_msg.visibility = toVisibility(!networkState?.msg.isNullOrEmpty())
        tv_error_msg.text = networkState?.msg
    }

    companion object {
        fun create(parent: ViewGroup, retryCallback: () -> Unit) = NetworkStateViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_network_state, parent, false),
            retryCallback
        )

        fun toVisibility(constraint: Boolean): Int =
            if (constraint) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }
}
