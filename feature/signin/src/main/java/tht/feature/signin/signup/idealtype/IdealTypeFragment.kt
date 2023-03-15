package tht.feature.signin.signup.idealtype

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.extension.repeatOnStarted
import tht.feature.signin.databinding.FragmentIdealTypeBinding
import tht.feature.signin.signup.SignupRootBaseFragment
import tht.feature.signin.signup.SignupRootViewModel

@AndroidEntryPoint
class IdealTypeFragment :
    SignupRootBaseFragment<IdealTypeViewModel, FragmentIdealTypeBinding>(FragmentIdealTypeBinding::inflate) {

    override val viewModel by viewModels<IdealTypeViewModel>()

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.IDEAL_TYPE)
    }

    override fun setListener() {
        binding.btnNext.setOnClickListener { rootViewModel.nextEvent(SignupRootViewModel.Step.IDEAL_TYPE) }
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

        val TAG = IdealTypeFragment::class.simpleName.toString()

        fun newInstance() = IdealTypeFragment()
    }
}
