package com.winli.rawg.utils

import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.winli.rawg.R

object ViewHelper {

    fun FloatingActionButton.setStatus(isFavorite: Boolean) {
        if (isFavorite) {
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_white_24dp))
        } else {
            setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_not_favorite_white_24dp
                )
            )
        }
    }
}