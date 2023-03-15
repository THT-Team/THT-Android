package tht.feature.signin.signup.introduction

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.extension.repeatOnStarted
import tht.feature.signin.databinding.FragmentIntroductionBinding
import tht.feature.signin.signup.SignupRootBaseFragment
import tht.feature.signin.signup.SignupRootViewModel

@AndroidEntryPoint
class IntroductionFragment : SignupRootBaseFragment<IntroductionViewModel, FragmentIntroductionBinding>(FragmentIntroductionBinding::inflate) {

    override val viewModel by viewModels<IntroductionViewModel>()

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.INTRODUCTION)
    }

    override fun setListener() {
        binding.btnNext.setOnClickListener { rootViewModel.nextEvent(SignupRootViewModel.Step.INTRODUCTION) }
    }

    override fun observeData() {
        repeatOnStarted {

            launch {
                viewModel.uiStateFlow.collect {
                }
            }

            launch {
                viewModel.sideEffectFlow.collect {
                }
            }
        }
    }

    companion object {

        val TAG = IntroductionFragment::class.simpleName.toString()

        fun newInstance() = IntroductionFragment()
    }
}
