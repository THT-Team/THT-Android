package tht.feature.like.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import tht.core.ui.delegate.viewBinding
import tht.feature.heart.R
import tht.feature.heart.databinding.DialogDetailBinding
import tht.feature.like.constant.LikeConstant
import tht.feature.like.like.LikeModel

@AndroidEntryPoint
class LikeDetailFragment : BottomSheetDialogFragment() {

    private val binding by viewBinding(DialogDetailBinding::inflate)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val likeUser = arguments?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                it.getSerializable(LikeConstant.KEY, LikeModel::class.java)
            else
                it.getSerializable(LikeConstant.KEY) as LikeModel
        }
        if (likeUser == null) dismiss()
        else {
            binding.apply {
                tvCategory.text = likeUser.nickname
                tvNickname.text = getString(R.string.nickname, likeUser.nickname, likeUser.age)
                tvAddress.text = likeUser.address
                tvIntroduction.text = likeUser.introduce

            }
        }

    }
}
