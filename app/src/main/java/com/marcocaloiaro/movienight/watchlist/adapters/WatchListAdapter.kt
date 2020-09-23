package com.marcocaloiaro.movienight.watchlist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcocaloiaro.movienight.R
import com.marcocaloiaro.movienight.details.model.ShowDetails
import com.marcocaloiaro.movienight.watchlist.utils.WatchListClickListener

class WatchListAdapter(val context: Context, val shows: MutableList<ShowDetails>, private val watchListClickListener : WatchListClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.watchlist_item, parent, false)
        return WatchListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return shows.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as WatchListViewHolder).bindData(shows[position])
        holder.showCover.setOnClickListener {
            watchListClickListener.onShowClicked(shows[position])
        }
        holder.deleteButton.setOnClickListener {
            watchListClickListener.onDeleteIconClicked(shows, shows[position])
            shows.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, shows.size)
        }
    }

    fun addData(list: List<ShowDetails>) {
        shows.clear()
        shows.addAll(list)
    }
}