package tht.feature.signin.signup.nickname

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
import tht.feature.signin.databinding.FragmentNicknameBinding
import tht.feature.signin.signup.SignupRootBaseFragment
import tht.feature.signin.signup.SignupRootViewModel
import tht.feature.signin.util.AnimatorUtil
import tht.feature.signin.util.StringUtil

@AndroidEntryPoint
class NicknameFragment : SignupRootBaseFragment<NicknameViewModel, FragmentNicknameBinding>() {

    override val binding: FragmentNicknameBinding by viewBinding(FragmentNicknameBinding::inflate)

    override val viewModel by viewModels<NicknameViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.NICKNAME)
    }

    override fun setListener() {
        binding.btnNext.setOnClickListener {
            viewModel.clickNextEvent(rootViewModel.phone.value, binding.etNickname.text?.toString())
        }

        binding.etNickname.addTextChangedListener {
            viewModel.textInputEvent(it?.toString())
        }

        binding.layoutBackground.setOnClickListener {
            viewModel.backgroundTouchEvent()
        }
    }

    private fun initView() {
        binding.layoutEtNickname.setEndIconTintList(null)
        binding.etNickname.filters = arrayOf(
            InputFilter.LengthFilter(NicknameViewModel.MAX_LENGTH)
        )
        viewModel.fetchSavedData(rootViewModel.phone.value)
        StringUtil.setWhiteTextColor(binding.tvNicknameTitle, 0 until 3)
    }

    override fun observeData() {
        repeatOnStarted {
            launch {
                viewModel.uiStateFlow.collect {
                    when (it) {
                        is NicknameViewModel.NicknameUiState.Default -> {
                            binding.layoutEtNickname.error = null
                            binding.btnNext.isEnabled = false
                        }

                        is NicknameViewModel.NicknameUiState.InvalidInput -> {
                            AnimatorUtil.hapticAnimation(binding.layoutEtNickname)
                            binding.layoutEtNickname.error = it.message
                            binding.btnNext.isEnabled = false
                        }

                        is NicknameViewModel.NicknameUiState.ValidInput -> {
                            binding.layoutEtNickname.error = null
                            binding.btnNext.isEnabled = true
                        }

                        is NicknameViewModel.NicknameUiState.InvalidPhoneNumber -> {
                            context?.showToast(it.message)
                            rootViewModel.backEvent()
                        }
                    }
                }
            }

            launch {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        is NicknameViewModel.NicknameSideEffect.ShowToast ->
                            context?.showToast(it.message)

                        is NicknameViewModel.NicknameSideEffect.KeyboardVisible ->
                            binding.etNickname.setSoftKeyboardVisible(it.visible)

                        is NicknameViewModel.NicknameSideEffect.NavigateNextView ->
                            rootViewModel.nextEvent(SignupRootViewModel.Step.NICKNAME)
                    }
                }
            }

            launch {
                viewModel.inputLength.collect {
                    binding.tvInputLength.text =
                        requireContext().getString(R.string.value_input_length, it, NicknameViewModel.MAX_LENGTH)
                }
            }

            launch {
                viewModel.dataLoading.collect {
                    binding.progress.isVisible = it
                }
            }

            launch {
                viewModel.inputValue.collect {
                    if (it != binding.etNickname.text.toString()) {
                        binding.etNickname.setText(it)
                    }
                }
            }
        }
    }

    companion object {

        val TAG = NicknameFragment::class.simpleName.toString()

        fun newInstance() = NicknameFragment()
    }
}
