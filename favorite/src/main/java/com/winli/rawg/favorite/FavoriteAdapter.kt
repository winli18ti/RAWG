package com.winli.rawg.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.winli.rawg.core.R
import com.winli.rawg.core.databinding.ItemGameBinding
import com.winli.rawg.core.ui.model.GameView
import com.winli.rawg.core.utils.Converter.changeIfEmpty
import com.winli.rawg.core.utils.Converter.toDate
import com.winli.rawg.core.utils.ViewHelper.loadImage

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {

    private var listData = ArrayList<GameView>()
    var onItemClick: ((GameView) -> Unit)? = null

    fun setData(newListData: List<GameView>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = ItemGameBinding.bind(itemView)
        fun bind(data: GameView) {
            with(binding) {
                ivItemImage.loadImage(data.backgroundImage)
                tvItemTitle.text = data.name.changeIfEmpty()
                tvItemSubtitle.text = data.released.toDate().changeIfEmpty()
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[bindingAdapterPosition])
            }
        }
    }

    fun getSwipedData(swipedPosition: Int) = listData[swipedPosition]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }
}