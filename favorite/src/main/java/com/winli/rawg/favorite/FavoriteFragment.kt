package com.winli.rawg.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.winli.rawg.core.ui.model.GameView
import com.winli.rawg.favorite.databinding.FragmentFavoriteBinding
import com.winli.rawg.favorite.di.favoriteModule
import com.winli.rawg.favorite.utils.ViewHelper.setConfig
import com.winli.rawg.ui.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private lateinit var favoriteAdapter: FavoriteAdapter
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding as FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding.rvGame)
        if (activity != null) {
            loadKoinModules(favoriteModule)

            favoriteAdapter = FavoriteAdapter()
            favoriteAdapter.onItemClick = { selectedData ->
                Intent(activity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_ID, selectedData.id)
                    startActivity(this)
                }
            }
            favoriteViewModel.gamesView.observe(viewLifecycleOwner, observer)
            binding.rvGame.setConfig(favoriteAdapter)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvGame.adapter = null
        _binding = null
    }

    private val observer = Observer<List<GameView>> { gamesView ->
        favoriteAdapter.setData(gamesView)
        showEmpty(gamesView.isEmpty())
    }

    private fun showEmpty(isEmpty: Boolean) {
        with(binding) {
            if (isEmpty) {
                lvEmpty.visibility = View.VISIBLE
                tvEmpty.visibility = View.VISIBLE
            } else {
                lvEmpty.visibility = View.GONE
                tvEmpty.visibility = View.GONE
            }
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.bindingAdapterPosition
                val gameView = favoriteAdapter.getSwipedData(swipedPosition)
                val game = favoriteViewModel.getGame(gameView)
                favoriteViewModel.setFavoriteGame(game)
                game.isFavorite = !game.isFavorite
                Snackbar.make(binding.rvGame, R.string.message_undo, Snackbar.LENGTH_LONG)
                    .setAction(R.string.message_ok) {
                        favoriteViewModel.setFavoriteGame(game)
                    }
                    .show()
            }
        }
    })
}