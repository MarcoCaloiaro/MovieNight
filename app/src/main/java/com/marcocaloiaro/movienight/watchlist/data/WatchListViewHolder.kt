package com.marcocaloiaro.movienight.watchlist.data

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marcocaloiaro.movienight.R
import com.marcocaloiaro.movienight.details.model.ShowDetails
import com.marcocaloiaro.movienight.base.utils.ImageUtils

class WatchListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val showCover : ImageView = view.findViewById(R.id.movie_image)
    private val showTitle : TextView = view.findViewById(R.id.show_title)
    val deleteButton : ImageButton = view.findViewById(R.id.delete_icon)

    fun bindData(showDetails: ShowDetails) {
        showTitle.text = showDetails.title
        if (ImageUtils.isShowPosterAvailable(showDetails.poster)) {
            Glide.with(view.context).load(showDetails.poster).into(showCover)
            return
        }
        Glide.with(view.context).load(
            ContextCompat.getDrawable(view.context,
            R.drawable.not_found
        )).into(showCover)
    }

}