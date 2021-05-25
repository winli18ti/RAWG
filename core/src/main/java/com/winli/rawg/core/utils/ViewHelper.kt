package com.winli.rawg.core.utils

import android.os.Build
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.winli.rawg.core.ui.GameAdapter

object ViewHelper {

    fun TextView.underline() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            text = Html.fromHtml(
                "<u>$text</u>",
                Html.FROM_HTML_MODE_LEGACY
            )
        }
    }

    fun ImageView.loadImage(path: String) {
        Glide.with(context)
            .load(path)
            .into(this)
    }

    fun RecyclerView.setConfig(gameAdapter: GameAdapter) {
        layoutManager = LinearLayoutManager(context)
        setHasFixedSize(true)
        adapter = gameAdapter
    }
}