package tht.feature.like.detail

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.dialog.setBottomSheetDialogSize
import tht.core.ui.dialog.showCustomAlertDialog
import tht.core.ui.extension.repeatOnStarted
import tht.feature.heart.R
import tht.feature.heart.databinding.DialogDetailBinding
import tht.feature.like.constant.LikeConstant
import tht.feature.like.like.LikeModel
import tht.feature.like.like.LikeViewModel

@AndroidEntryPoint
class LikeDetailFragment : BottomSheetDialogFragment() {

    private val binding by viewBinding(DialogDetailBinding::inflate)
    private val parentViewModel: LikeViewModel by viewModels({ requireParentFragment() })
    private val viewModel: LikeDetailViewModel by viewModels()

    private lateinit var likeUser: LikeModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, tht.core.ui.R.style.TransparentBottomSheet)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            setBottomSheetDialogSize(requireActivity(), it as BottomSheetDialog)
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
        observeData()
    }

    private fun initView() {
        likeUser = arguments?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                it.getSerializable(LikeConstant.KEY, LikeModel::class.java)
            else
                it.getSerializable(LikeConstant.KEY) as LikeModel
        } ?: LikeModel.getDefaultLikeModel()
        if (likeUser.nickname.isEmpty()) dismiss()
        else {
            binding.apply {
                svDetail.clipToOutline = true
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
        binding.ivClose.setOnClickListener {
            dismiss()
        }
        binding.ivReport.setOnClickListener {
            viewModel.showReportOrBlockDialogEvent()
        }
        binding.btnNextChance.setOnClickListener {
            parentViewModel.nextClickListener(likeUser.nickname)
            dismiss()
        }
        binding.btnChatting.setOnClickListener { }
    }

    private fun observeData() {
        repeatOnStarted {
            launch {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        is LikeDetailViewModel.LikeDetailSideEffect.ShowBlockDialog -> {
                            showBlockDialog()
                        }

                        is LikeDetailViewModel.LikeDetailSideEffect.ShowReportDialog -> {
                            showReportDialog()
                        }

                        LikeDetailViewModel.LikeDetailSideEffect.ShowReportOrBlockDialog -> {
                            showBlockOrReportDialog()
                        }
                    }
                }
            }
        }
    }

    private fun showBlockOrReportDialog() =
        showCustomAlertDialog(
            requireContext(),
            requireActivity(),
            arrayOf(
                requireContext().getString(R.string.menu_report),
                requireContext().getString(R.string.menu_block),
            ),
            R.layout.item_dialog_subt2_semi_bold,
            true,
            itemClickListener = { dialog, which ->
                when (which) {
                    0 -> viewModel.showReportDialogEvent()
                    1 -> viewModel.blockEvent()
                }
                dialog.dismiss()
            }
        )

    private fun showReportDialog() =
        showCustomAlertDialog(
            requireContext(),
            requireActivity(),
            arrayOf(
                requireContext().getString(R.string.report_content_unpleasant_picture),
                requireContext().getString(R.string.report_content_false_profile),
                requireContext().getString(R.string.report_content_steal_picture),
                requireContext().getString(R.string.report_content_swear_word),
                requireContext().getString(R.string.report_content_illegality),
            ),
            R.layout.item_dialog_subt2_regular,
            titleText = requireContext().getString(R.string.report_title),
            dividerVisibility = false,
            footerText = requireContext().getString(R.string.cancel),
            itemClickListener = { dialog, which ->
                when (which) {
                    0 -> viewModel.reportUser(requireContext().getString(R.string.report_content_unpleasant_picture))
                    1 -> viewModel.reportUser(requireContext().getString(R.string.report_content_false_profile))
                    2 -> viewModel.reportUser(requireContext().getString(R.string.report_content_steal_picture))
                    3 -> viewModel.reportUser(requireContext().getString(R.string.report_content_swear_word))
                    4 -> viewModel.reportUser(requireContext().getString(R.string.report_content_illegality))
                }
                dialog.dismiss()
            }
        )

    private fun showBlockDialog() =
        showCustomAlertDialog(
            requireContext(),
            requireActivity(),
            arrayOf(
                requireContext().getString(R.string.menu_block),
                requireContext().getString(R.string.cancel),
            ),
            R.layout.item_dialog_subt2_semi_bold,
            titleText = requireContext().getString(R.string.block_title),
            subTitleText = requireContext().getString(R.string.block_content),
            dividerVisibility = true,
            itemClickListener = { dialog, which ->
                when (which) {
                    0 -> viewModel.blockUser()
                }
                dialog.dismiss()
            }
        )

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
