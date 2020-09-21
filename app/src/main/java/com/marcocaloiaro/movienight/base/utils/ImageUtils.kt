package com.marcocaloiaro.movienight.base.utils

class ImageUtils private constructor() {

    companion object {
        fun isShowPosterAvailable(showPosterUrl: String) : Boolean {
            return showPosterUrl != Constants.SHOW_NOT_AVAILABLE
        }
    }

}