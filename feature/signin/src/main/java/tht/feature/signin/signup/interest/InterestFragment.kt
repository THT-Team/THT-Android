package tht.feature.signin.signup.interest

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.tht.tht.domain.signup.model.InterestModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.getPxFromDp
import tht.core.ui.extension.repeatOnStarted
import tht.core.ui.extension.showToast
import tht.feature.signin.R
import tht.feature.signin.databinding.FragmentInterestBinding
import tht.feature.signin.signup.SignupRootBaseFragment
import tht.feature.signin.signup.SignupRootViewModel
import tht.feature.signin.util.StringUtil

@AndroidEntryPoint
class InterestFragment : SignupRootBaseFragment<InterestViewModel, FragmentInterestBinding>() {

    override val binding: FragmentInterestBinding by viewBinding(FragmentInterestBinding::inflate)

    override val viewModel by viewModels<InterestViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModel.fetchSavedData(rootViewModel.phone.value)
    }

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.INTEREST)
    }

    override fun setListener() {
        binding.btnNext.setOnClickListener {
            viewModel.nextEvent(rootViewModel.phone.value)
        }
    }

    private fun initView() {
        binding.tvInterestDescription.text =
            getString(R.string.message_interest, InterestViewModel.MAX_REQUIRE_SELECT_COUNT)
        StringUtil.setWhiteTextColor(binding.tvInterestTitle, 0 until 5)
        StringUtil.setWhiteTextColor(binding.tvInterestDescription, 0 until 5)
    }

    override fun observeData() {
        repeatOnStarted {
            launch {
                viewModel.uiStateFlow.collect {
                    when (it) {
                        is InterestViewModel.InterestTypeUiState.InvalidPhoneNumber -> {}

                        is InterestViewModel.InterestTypeUiState.FetchListFail -> {
                            context?.showToast(it.message)
                            rootViewModel.backEvent()
                        }

                        is InterestViewModel.InterestTypeUiState.LessRequireSelectCount ->
                            binding.btnNext.isEnabled = false

                        is InterestViewModel.InterestTypeUiState.FullRequireSelectCount ->
                            binding.btnNext.isEnabled = true
                    }
                }
            }

            launch {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        is InterestViewModel.InterestTypeSideEffect.ShowToast ->
                            requireContext().showToast(it.message)

                        is InterestViewModel.InterestTypeSideEffect.NavigateNextView ->
                            rootViewModel.nextEvent(SignupRootViewModel.Step.INTEREST)
                    }
                }
            }

            launch {
                viewModel.selectInterest.collect { set ->
                    binding.groupChip.children.forEachIndexed { idx, view ->
                        if (idx !in 0 until viewModel.interestList.value.size)
                            return@forEachIndexed
                        val interest = viewModel.interestList.value[idx]
                        (view as? Chip)?.isSelected = set.contains(interest)
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
            viewModel.interestList.collect {
                it.forEachIndexed { idx, interest -> addChip(interest, idx) }
            }
        }
    }

    private fun getInterestChip(): Chip = Chip(requireContext()).apply {
        setTextColor(resources.getColor(tht.core.ui.R.color.white_f9fafa, null))
        setChipBackgroundColorResource(R.color.selector_ideal_chip_color)
        chipStrokeWidth = requireContext().getPxFromDp(1)
        setChipStrokeColorResource(tht.core.ui.R.color.gray_8d8d8d)
    }

    private fun addChip(interest: InterestModel, idx: Int) {
        val code = Integer.decode("0x${interest.emojiCode}")
        val emoji = String(Character.toChars(code))
        binding.groupChip.addView(
            getInterestChip().apply {
                "$emoji ${interest.title}".let {
                    text = it
                }
                setOnClickListener {
                    viewModel.interestChipClickEvent(idx)
                }
            }
        )
    }

    companion object {

        val TAG = InterestFragment::class.simpleName.toString()

        fun newInstance() = InterestFragment()
    }
}
