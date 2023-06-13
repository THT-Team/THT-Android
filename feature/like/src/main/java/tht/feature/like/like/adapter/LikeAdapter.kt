package tht.feature.like.like.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tht.feature.heart.databinding.ItemContentBinding
import tht.feature.heart.databinding.ItemHeaderBinding
import tht.feature.like.like.LikeModel

class LikeAdapter(
    private val imageClickListener: (LikeModel) -> Unit,
    private val nextClickListener: (String) -> Unit
) : ListAdapter<LikeItem, RecyclerView.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER -> LikeHeaderViewHolder(
                ItemHeaderBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

            else -> LikeContentViewHolder(
                ItemContentBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LikeHeaderViewHolder -> holder.bind(
                (getItem(position) as LikeItem.Header).category
            )
            is LikeContentViewHolder -> holder.bind(
                imageClickListener,
                nextClickListener,
                (getItem(position) as LikeItem.Content).item
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            (holder as LikeContentViewHolder).bind((getItem(position) as LikeItem.Content).item.isNew)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is LikeItem.Header -> HEADER
            is LikeItem.Content -> CONTENT
        }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<LikeItem>() {
            override fun areItemsTheSame(oldItem: LikeItem, newItem: LikeItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: LikeItem, newItem: LikeItem): Boolean =
                oldItem == newItem

            override fun getChangePayload(oldItem: LikeItem, newItem: LikeItem): Any? {
                if (oldItem is LikeItem.Content && newItem is LikeItem.Content) {
                    if (oldItem.item.isNew != newItem.item.isNew)
                        return newItem.item.isNew
                }

                return super.getChangePayload(oldItem, newItem)
            }
        }

        const val HEADER = 0
        const val CONTENT = 1
    }
}
