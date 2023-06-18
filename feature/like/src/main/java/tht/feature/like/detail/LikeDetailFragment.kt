package tht.feature.like.detail

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.WindowMetrics
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.getPxFromDp
import tht.core.ui.extension.repeatOnStarted
import tht.feature.heart.R
import tht.feature.heart.databinding.DialogDetailBinding
import tht.feature.like.constant.LikeConstant
import tht.feature.like.like.LikeModel
import tht.feature.like.like.LikeViewModel
import kotlin.math.roundToInt

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
            viewModel.showReportDialogEvent()
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
                        is LikeDetailViewModel.LikeDetailSideEffect.Block -> {

                        }

                        is LikeDetailViewModel.LikeDetailSideEffect.Report -> {

                        }

                        LikeDetailViewModel.LikeDetailSideEffect.ShowReportDialog -> {
                            showReportDialog()
                        }
                    }
                }
            }
        }
    }

    private fun showReportDialog() {
        val menus = arrayOf(
            requireContext().getString(R.string.menu_report),
            requireContext().getString(R.string.menu_block)
        )

        val dialog = AlertDialog.Builder(requireContext(), R.style.ReportDialogStyle)
            .setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.item_dialog_subt2_text,
                    menus
                )
            ) { dialog, which ->
                when (which) {
                    0 -> viewModel.reportEvent(likeUser.nickname)
                    1 -> viewModel.blockEvent(likeUser.nickname)
                }
                dialog.dismiss()
            }
            .create()

        dialog.setOnShowListener {
            dialog.window?.setLayout(
                getWindowSize().second * 85 / 100,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }

        dialog.apply {
            listView.divider = ColorDrawable(resources.getColor(tht.core.ui.R.color.gray_666666, null))
            listView.dividerHeight = requireContext().getPxFromDp(1.5f).roundToInt()
            listView.setFooterDividersEnabled(false)
            listView.addFooterView(View(requireContext()))
        }.show()
    }

    private fun setDialogSize(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
        val behavior = BottomSheetBehavior.from<View>(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        val height = getWindowSize().first * 85 / 100
        layoutParams.height = height
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.peekHeight = height
    }

    private fun getWindowSize(): Pair<Int, Int> =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics: WindowMetrics = requireActivity().windowManager.currentWindowMetrics
            val bounds = windowMetrics.bounds
            bounds.height() to bounds.width()
        } else {
            val displayMetrics = DisplayMetrics()
            val display = requireActivity().windowManager.defaultDisplay
            display.getRealMetrics(displayMetrics)
            displayMetrics.heightPixels to displayMetrics.widthPixels
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
