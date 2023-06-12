package tht.feature.like.like.adapter

import androidx.recyclerview.widget.RecyclerView
import tht.feature.heart.databinding.ItemHeaderBinding

class LikeHeaderViewHolder(
    private val binding: ItemHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(category: String) {
        binding.apply {
            tvCategory.text = category
        }
    }
}
