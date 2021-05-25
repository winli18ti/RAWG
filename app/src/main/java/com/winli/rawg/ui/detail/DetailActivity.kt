package com.winli.rawg.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.winli.rawg.R
import com.winli.rawg.core.data.Resource
import com.winli.rawg.core.ui.model.GameDetailView
import com.winli.rawg.core.utils.Converter.changeIfEmpty
import com.winli.rawg.core.utils.Converter.toDate
import com.winli.rawg.core.utils.ViewHelper.loadImage
import com.winli.rawg.core.utils.ViewHelper.underline
import com.winli.rawg.databinding.ActivityDetailBinding
import com.winli.rawg.utils.ViewHelper.setStatus
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModel()
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        detailViewModel.setId(id)
        detailViewModel.gameDetailView.observe(this, observer)

        binding.fabBack.setOnClickListener {
            onBackPressed()
        }
    }

    private val observer = Observer<Resource<GameDetailView>> { gameDetailView ->
        if (gameDetailView != null) {
            when (gameDetailView) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    showLoading(false)
                    setDetailGame(gameDetailView.data as GameDetailView)
                }
                is Resource.Error -> {
                    showError()
                    Snackbar.make(
                        binding.root,
                        gameDetailView.message.toString(),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.root.visibility = View.VISIBLE
        } else {
            binding.progressBar.root.visibility = View.GONE
        }
    }

    private fun showError() {
        with(binding) {
            binding.progressBar.root.visibility = View.GONE
            viewError.root.visibility = View.VISIBLE
        }
    }

    private fun setDetailGame(game: GameDetailView) {
        with(game) {
            with(binding) {
                toolbar.title = name.changeIfEmpty()
                ivDetailImage.loadImage(backgroundImage)
            }

            with(binding.content) {
                tvName.text = name.changeIfEmpty()
                tvDescription.text = description.changeIfEmpty()
                tvMetacritic.text = metacritic.toString()
                tvReleased.text = released.toDate().changeIfEmpty()
                tvReleasedSubtitle.text = released.toDate().changeIfEmpty()
                tvWebsite.text = website.changeIfEmpty()
                tvRating.text = rating.toString()
                tvPlaytime.text =
                    resources.getQuantityString(R.plurals.playtime, playtime, playtime)
                tvRatingsCount.text =
                    resources.getQuantityString(
                        R.plurals.ratings_count,
                        game.ratingsCount,
                        game.ratingsCount
                    )
                tvPlatforms.text = platforms.changeIfEmpty()
                tvStores.text = stores.changeIfEmpty()
                tvDevelopers.text = developers.changeIfEmpty()
                tvGenres.text = genres.changeIfEmpty()
                tvTags.text = tags.changeIfEmpty()
                tvPublishers.text = publishers.changeIfEmpty()
                tvEsrbRating.text = esrbRating.changeIfEmpty()

                if (website.isNotEmpty()) {
                    tvWebsite.apply {
                        underline()
                        setOnClickListener {
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse(website)
                            }
                            startActivity(intent)
                        }
                    }
                }
            }

            with(binding.fabFavorite) {
                setStatus(isFavorite)
                setOnClickListener {
                    isFavorite = !isFavorite
                    detailViewModel.setFavoriteGame(isFavorite)
                    setStatus(isFavorite)
                }
            }
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}