package tht.feature.like.detail

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowMetrics
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import tht.core.ui.delegate.viewBinding
import tht.feature.heart.R
import tht.feature.heart.databinding.DialogDetailBinding
import tht.feature.like.constant.LikeConstant
import tht.feature.like.like.LikeModel

@AndroidEntryPoint
class LikeDetailFragment : BottomSheetDialogFragment() {

    private val binding by viewBinding(DialogDetailBinding::inflate)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            setDialogSize(it as BottomSheetDialog)
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setOnClickListener()
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
                tvCategory.text = likeUser.category
                tvNickname.text = getString(R.string.nickname, likeUser.nickname, likeUser.age)
                tvAddress.text = likeUser.address
                tvIntroduction.text = likeUser.introduce
                likeUser.idealTypes.forEachIndexed { index, idealType ->
                    addChip(binding.cgIdealType, idealType.emojiCode, idealType.title, index)
                }
                likeUser.interests.forEachIndexed { index, interest ->
                    addChip(binding.cgInterests, interest.emojiCode, interest.title, index)
                }
                val images = listOf(binding.ivOne, binding.ivTwo, binding.ivThree)
                likeUser.profileImgUrl.forEachIndexed { index, url ->
                    loadImage(images[index], url)
                }
            }
        }
    }

    private fun setOnClickListener() {
        binding.ivClose.setOnClickListener { dismiss() }
        binding.btnNextChance.setOnClickListener {  }
        binding.btnChatting.setOnClickListener {  }
    }

    private fun setDialogSize(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
        val behavior = BottomSheetBehavior.from<View>(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        val height = getBottomSheetDialogDefaultHeight()
        layoutParams.height = height
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.peekHeight = height
    }

    private fun getBottomSheetDialogDefaultHeight(): Int {
        return getWindowHeight() * 80 / 100
    }

    private fun getWindowHeight(): Int =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics: WindowMetrics = requireActivity().windowManager.currentWindowMetrics
            val bounds = windowMetrics.bounds
            bounds.height()
        } else {
            val displayMetrics = DisplayMetrics()
            val display = requireActivity().windowManager.defaultDisplay
            display.getRealMetrics(displayMetrics)
            displayMetrics.heightPixels
        }

    private fun loadImage(view: ImageView, url: String) {
        Glide.with(view)
            .load(url)
            .into(view)
    }

    private fun addChip(chipGroup: ChipGroup, emojiCode: String, title: String, idx: Int) {
        val code = Integer.decode("0x$emojiCode")
        val emoji = String(Character.toChars(code))
        chipGroup.addView(
            getChip().apply {
                "$emoji $title".let {
                    text = it
                }
            }
        )
    }

    private fun getChip(): Chip = Chip(requireContext()).apply {
        setTextColor(resources.getColor(tht.core.ui.R.color.white_f9fafa, null))
        setChipBackgroundColorResource(tht.core.ui.R.color.black_111111)
    }
}
