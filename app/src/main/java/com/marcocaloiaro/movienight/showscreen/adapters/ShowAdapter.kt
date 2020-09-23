package com.marcocaloiaro.movienight.showscreen.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcocaloiaro.movienight.R
import com.marcocaloiaro.movienight.showscreen.base.utils.ShowClickListener
import com.marcocaloiaro.movienight.showscreen.model.Show

class ShowAdapter (val context: Context, private val showsList: MutableList<Show>, private val showClickListener: ShowClickListener) :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        return ShowViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int {
        return showsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ShowViewHolder).bindData(showsList[position])
        holder.itemView.setOnClickListener {
            showClickListener.onShowClicked(showsList[position])
        }
    }

    fun addData(list: List<Show>) {
        showsList.clear()
        showsList.addAll(list)
    }
}