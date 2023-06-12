package tht.feature.like

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tht.feature.heart.databinding.ItemContentBinding
import tht.feature.heart.databinding.ItemHeaderBinding

class LikeAdapter : ListAdapter<LikeUserModel, RecyclerView.ViewHolder>(diffUtil) {

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
            is LikeHeaderViewHolder -> holder.bind(getItem(position).nickname)
            is LikeContentViewHolder -> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (position == 0)
            HEADER
        else
            CONTENT

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<LikeUserModel>() {
            override fun areItemsTheSame(oldItem: LikeUserModel, newItem: LikeUserModel): Boolean {
                Log.d("Testtt", oldItem.toString() + "\n" + newItem.toString())
                return oldItem.nickname == newItem.nickname
            }

            override fun areContentsTheSame(oldItem: LikeUserModel, newItem: LikeUserModel): Boolean {
                Log.d("Testtt", (oldItem == newItem).toString())
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: LikeUserModel, newItem: LikeUserModel): Any? {
                Log.d("Testtt", oldItem.toString() + "\n" + newItem.toString())
                return super.getChangePayload(oldItem, newItem)
            }
        }

        const val HEADER = 0
        const val CONTENT = 1
    }
}
