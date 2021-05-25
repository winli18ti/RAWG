package com.winli.rawg.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.winli.rawg.core.ui.GameAdapter
import com.winli.rawg.core.ui.model.GameView
import com.winli.rawg.core.utils.ViewHelper.setConfig
import com.winli.rawg.databinding.FragmentHomeBinding
import com.winli.rawg.ui.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var gameAdapter: GameAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            gameAdapter = GameAdapter()
            gameAdapter.onItemClick = { selectedData ->
                Intent(activity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_ID, selectedData.id)
                    startActivity(this)
                }
            }

            homeViewModel.gamesView.observe(viewLifecycleOwner, observer)
            binding.rvGame.setConfig(gameAdapter)
            binding.searchView.apply {
                setOnClickListener {
                    onActionViewExpanded()
                }
                setOnQueryTextListener(textListener)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvGame.adapter = null
        binding.searchView.setOnQueryTextListener(null)
        _binding = null
    }

    private val observer = Observer<List<GameView>> { gamesView ->
        gameAdapter.setData(gamesView)
        showLoading(false)
        binding.viewEmpty.root.visibility = if (gamesView.isEmpty()) View.VISIBLE else View.GONE
    }

    private val textListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            showLoading(true)
            homeViewModel.setSearch(query)
            return true
        }

        override fun onQueryTextChange(query: String): Boolean {
            showLoading(true)
            homeViewModel.setSearch(query)
            return true
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.rvGame.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            binding.viewEmpty.root.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvGame.visibility = View.VISIBLE
        }
    }
}