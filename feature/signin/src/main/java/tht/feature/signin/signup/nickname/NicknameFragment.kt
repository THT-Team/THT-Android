package tht.feature.signin.signup.nickname

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.extension.repeatOnStarted
import tht.feature.signin.databinding.FragmentNicknameBinding
import tht.feature.signin.signup.SignupRootBaseFragment
import tht.feature.signin.signup.SignupRootViewModel

@AndroidEntryPoint
class NicknameFragment : SignupRootBaseFragment<NicknameViewModel, FragmentNicknameBinding>(FragmentNicknameBinding::inflate) {

    override val viewModel by viewModels<NicknameViewModel>()

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.NICKNAME)
    }

    override fun setListener() {
        binding.btnNext.setOnClickListener { rootViewModel.nextEvent(SignupRootViewModel.Step.NICKNAME) }
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

        val TAG = NicknameFragment::class.simpleName.toString()

        fun newInstance() = NicknameFragment()
    }
}
