package tht.feature.like.like.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
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

    companion object {
        fun getInstance(parent: ViewGroup) =
            LikeHeaderViewHolder(
                ItemHeaderBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

    }
}
