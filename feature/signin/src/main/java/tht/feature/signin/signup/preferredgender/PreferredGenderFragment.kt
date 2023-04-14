package tht.feature.signin.signup.preferredgender

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.core.ui.extension.showToast
import tht.feature.signin.databinding.FragmentPreferredGenderBinding
import tht.feature.signin.signup.SignupRootBaseFragment
import tht.feature.signin.signup.SignupRootViewModel
import tht.feature.signin.util.StringUtil

@AndroidEntryPoint
class PreferredGenderFragment : SignupRootBaseFragment<PreferredGenderViewModel, FragmentPreferredGenderBinding>() {

    override val binding: FragmentPreferredGenderBinding by viewBinding(FragmentPreferredGenderBinding::inflate)

    override val viewModel by viewModels<PreferredGenderViewModel>()
    private lateinit var genderBtnList: List<AppCompatButton>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        genderBtnList = listOf(binding.btnMale, binding.btnFemale, binding.btnAllGender)
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.GENDER)
    }

    override fun setListener() {
        genderBtnList.forEachIndexed { i, btn ->
            btn.setOnClickListener {
                viewModel.selectGenderEvent(i)
            }
        }

        binding.btnNext.setOnClickListener {
            viewModel.nextEvent(rootViewModel.phone.value)
        }
    }

    private fun initView() {
        StringUtil.setWhiteTextColor(binding.tvPreferredGenderTitle, 0 until 5)
        viewModel.fetchSavedData(rootViewModel.phone.value)
    }

    override fun observeData() {
        repeatOnStarted {
            launch {
                viewModel.uiStateFlow.collect {
                    when (it) {
                        is PreferredGenderViewModel.PreferredGenderUiState.InvalidPhoneNumber -> {
                            context?.showToast(it.message)
                            rootViewModel.backEvent()
                        }

                        is PreferredGenderViewModel.PreferredGenderUiState.Default -> {
                            genderBtnList.forEach { btn ->
                                btn.isSelected = false
                            }
                            binding.btnNext.isEnabled = false
                        }

                        is PreferredGenderViewModel.PreferredGenderUiState.SelectedGender -> {
                            if (it.idx !in genderBtnList.indices) {
                                binding.btnNext.isEnabled = false
                                return@collect
                            }
                            genderBtnList.forEachIndexed { i, btn -> btn.isSelected = it.idx == i }
                            binding.btnNext.isEnabled = true
                        }
                    }
                }
            }

            launch {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        is PreferredGenderViewModel.PreferredGenderSideEffect.ShowToast ->
                            context?.showToast(it.message)

                        is PreferredGenderViewModel.PreferredGenderSideEffect.NavigateNextView ->
                            rootViewModel.nextEvent(SignupRootViewModel.Step.GENDER)
                    }
                }
            }
        }
    }

    companion object {

        val TAG = PreferredGenderFragment::class.simpleName.toString()

        fun newInstance() = PreferredGenderFragment()
    }
}
