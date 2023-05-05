package tht.feature.signin.signup.introduction

import android.os.Bundle
import android.text.InputFilter
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.core.ui.extension.setSoftKeyboardVisible
import tht.core.ui.extension.showToast
import tht.feature.signin.R
import tht.feature.signin.databinding.FragmentIntroductionBinding
import tht.feature.signin.signup.SignupRootBaseFragment
import tht.feature.signin.signup.SignupRootViewModel
import tht.feature.signin.util.StringUtil

@AndroidEntryPoint
class IntroductionFragment : SignupRootBaseFragment<IntroductionViewModel, FragmentIntroductionBinding>() {

    override val binding: FragmentIntroductionBinding by viewBinding(FragmentIntroductionBinding::inflate)

    override val viewModel by viewModels<IntroductionViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.INTRODUCTION)
    }

    override fun setListener() {
        binding.btnNext.setOnClickListener {
            viewModel.clickNextEvent(rootViewModel.phone.value, binding.etIntroduce.text?.toString())
        }

        binding.etIntroduce.addTextChangedListener {
            viewModel.textInputEvent(it?.toString())
        }

        binding.layoutBackground.setOnClickListener {
            viewModel.backgroundTouchEvent()
        }
    }

    private fun initView() {
        binding.layoutEtIntroduce.setEndIconTintList(null)
        binding.etIntroduce.filters = arrayOf(
            InputFilter.LengthFilter(IntroductionViewModel.MAX_LENGTH)
        )
        viewModel.fetchSavedData(rootViewModel.phone.value)
        StringUtil.setWhiteTextColor(binding.tvIntroduceTitle, 0 until 4)
    }

    override fun observeData() {
        repeatOnStarted {
            launch {
                viewModel.uiStateFlow.collect {
                    when (it) {
                        is IntroductionViewModel.IntroductionUiState.InvalidPhoneNumber -> {
                            context?.showToast(it.message)
                            rootViewModel.backEvent()
                        }

                        is IntroductionViewModel.IntroductionUiState.Empty -> {
                            binding.btnNext.isEnabled = false
                        }

                        is IntroductionViewModel.IntroductionUiState.ValidInput -> {
                            binding.etIntroduce.setText(it.introduce)
                            binding.btnNext.isEnabled = true
                        }
                    }
                }
            }

            launch {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        is IntroductionViewModel.IntroductionSideEffect.ShowToast ->
                            context?.showToast(it.message)

                        is IntroductionViewModel.IntroductionSideEffect.KeyboardVisible ->
                            binding.etIntroduce.setSoftKeyboardVisible(it.visible)

                        is IntroductionViewModel.IntroductionSideEffect.NavigateNextView ->
                            rootViewModel.nextEvent(SignupRootViewModel.Step.INTRODUCTION)
                    }
                }
            }

            launch {
                viewModel.inputLength.collect {
                    binding.tvInputLength.text =
                        requireContext().getString(R.string.value_input_length, it, IntroductionViewModel.MAX_LENGTH)
                }
            }

            launch {
                viewModel.dataLoading.collect {
                    binding.progress.isVisible = it
                }
            }
        }
    }

    companion object {

        val TAG = IntroductionFragment::class.simpleName.toString()

        fun newInstance() = IntroductionFragment()
    }
}
