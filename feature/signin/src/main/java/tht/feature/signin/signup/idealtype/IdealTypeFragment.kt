package tht.feature.signin.signup.idealtype

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.tht.tht.domain.signup.model.IdealTypeModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.getPxFromDp
import tht.core.ui.extension.repeatOnStarted
import tht.core.ui.extension.showToast
import tht.feature.signin.R
import tht.feature.signin.databinding.FragmentIdealTypeBinding
import tht.feature.signin.signup.SignupRootBaseFragment
import tht.feature.signin.signup.SignupRootViewModel
import tht.feature.signin.util.StringUtil

@AndroidEntryPoint
class IdealTypeFragment : SignupRootBaseFragment<IdealTypeViewModel, FragmentIdealTypeBinding>() {

    override val binding: FragmentIdealTypeBinding by viewBinding(FragmentIdealTypeBinding::inflate)

    override val viewModel by viewModels<IdealTypeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModel.fetchSavedData(rootViewModel.phone.value)
    }

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.IDEAL_TYPE)
    }

    override fun setListener() {
        binding.btnNext.setOnClickListener {
            viewModel.nextEvent(rootViewModel.phone.value)
        }
    }

    private fun initView() {
        binding.tvIdealDescription.text =
            getString(R.string.message_ideal_type, IdealTypeViewModel.MAX_REQUIRE_SELECT_COUNT)
        StringUtil.setWhiteTextColor(binding.tvIdealTitle, 0 until 5)
        StringUtil.setWhiteTextColor(binding.tvIdealDescription, 0 until 5)
    }

    override fun observeData() {
        repeatOnStarted {
            launch {
                viewModel.uiStateFlow.collect {
                    when (it) {
                        is IdealTypeViewModel.IdealTypeUiState.InvalidPhoneNumber -> {}

                        is IdealTypeViewModel.IdealTypeUiState.FetchListFail -> {
                            context?.showToast(it.message)
                            rootViewModel.backEvent()
                        }

                        is IdealTypeViewModel.IdealTypeUiState.LessRequireSelectCount ->
                            binding.btnNext.isEnabled = false

                        is IdealTypeViewModel.IdealTypeUiState.FullRequireSelectCount ->
                            binding.btnNext.isEnabled = true
                    }
                }
            }

            launch {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        is IdealTypeViewModel.IdealTypeSideEffect.ShowToast -> {}

                        is IdealTypeViewModel.IdealTypeSideEffect.NavigateNextView ->
                            rootViewModel.nextEvent(SignupRootViewModel.Step.IDEAL_TYPE)
                    }
                }
            }

            launch {
                viewModel.selectIdealTypes.collect { set ->
                    binding.groupChip.children.forEachIndexed { idx, view ->
                        if (idx !in 0 until viewModel.idealTypeList.value.size)
                            return@forEachIndexed
                        val ideal = viewModel.idealTypeList.value[idx]
                        (view as? Chip)?.isSelected = set.contains(ideal)
                    }
                }
            }

            launch {
                viewModel.dataLoading.collect {
                    binding.progress.isVisible = it
                }
            }
        }

        lifecycleScope.launch {
            viewModel.idealTypeList.collect {
                it.forEachIndexed { idx, ideal -> addChip(ideal, idx) }
            }
        }
    }

    private fun getIdealChip(): Chip = Chip(requireContext()).apply {
        setTextColor(resources.getColor(tht.core.ui.R.color.white_f9fafa, null))
        setChipBackgroundColorResource(R.color.selector_ideal_chip_color)
        chipStrokeWidth = requireContext().getPxFromDp(1)
        setChipStrokeColorResource(tht.core.ui.R.color.gray_8d8d8d)
    }

    private fun addChip(ideal: IdealTypeModel, idx: Int) {
        val code = Integer.decode("0x${ideal.emojiCode}")
        val emoji = String(Character.toChars(code))
        binding.groupChip.addView(
            getIdealChip().apply {
                "$emoji ${ideal.title}".let {
                    text = it
                }
                setOnClickListener {
                    viewModel.idealChipClickEvent(idx)
                }
            }
        )
    }

    companion object {
        val TAG = IdealTypeFragment::class.simpleName.toString()

        fun newInstance() = IdealTypeFragment()
    }
}
