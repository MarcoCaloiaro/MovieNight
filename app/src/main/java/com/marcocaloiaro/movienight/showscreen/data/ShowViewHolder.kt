package com.marcocaloiaro.movienight.showscreen.data

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marcocaloiaro.movienight.R
import com.marcocaloiaro.movienight.showscreen.model.Show
import com.marcocaloiaro.movienight.base.utils.ImageUtils.Companion.isShowPosterAvailable

class ShowViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private var showName: TextView = view.findViewById(R.id.show_title)
    private var showCover : ImageView = view.findViewById(R.id.movie_image)

    fun bindData(movie: Show) {
        showName.text = movie.title
        if (isShowPosterAvailable(movie.poster)) {
            Glide.with(view.context).load(movie.poster).into(showCover)
            return
        }
        Glide.with(view.context).load(ContextCompat.getDrawable(view.context, R.drawable.not_found)).into(showCover)
    }


}