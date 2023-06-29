package tht.feature.like.like.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import tht.core.ui.extension.getPxFromDp
import tht.feature.heart.R
import tht.feature.heart.databinding.ItemContentBinding
import tht.feature.like.like.LikeModel

class LikeContentViewHolder(
    private val binding: ItemContentBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        imageClickListener: (LikeModel) -> Unit,
        nextChanceClickListener: (String) -> Unit,
        likeUser: LikeModel
    ) {
        binding.apply {
            loadImage(binding.ivProfile, likeUser.profileImgUrl[0])
            tvNickname.text = tvNickname.context.getString(R.string.nickname, likeUser.nickname, likeUser.age)
            tvAddress.text = likeUser.address
            viewNewCircle.isVisible = likeUser.isNew
            btnNextChance.setOnClickListener { nextChanceClickListener(likeUser.nickname) }
            ivProfile.setOnClickListener { imageClickListener(likeUser) }
        }
    }

    fun bind(isNew: Boolean) {
        binding.viewNewCircle.isVisible = isNew
    }

    private fun loadImage(imageView: ImageView, url: String) {
        Glide.with(imageView)
            .load(url)
            .apply(
                RequestOptions().transform(
                    CenterCrop(),
                    RoundedCorners(
                        imageView.context.getPxFromDp(12).toInt()
                    )
                )
            )
            .into(imageView)
    }

    companion object {
        fun getInstance(parent: ViewGroup) =
            LikeContentViewHolder(
                ItemContentBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
    }
}
