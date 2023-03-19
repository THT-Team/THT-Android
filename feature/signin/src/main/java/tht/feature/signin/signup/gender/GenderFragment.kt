package tht.feature.signin.signup.gender

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.feature.signin.databinding.FragmentGenderBinding
import tht.feature.signin.signup.SignupRootBaseFragment
import tht.feature.signin.signup.SignupRootViewModel

@AndroidEntryPoint
class GenderFragment : SignupRootBaseFragment<GenderViewModel, FragmentGenderBinding>() {

    override val binding: FragmentGenderBinding by viewBinding(FragmentGenderBinding::inflate)

    override val viewModel by viewModels<GenderViewModel>()

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.GENDER)
    }

    override fun setListener() {
        binding.btnNext.setOnClickListener { rootViewModel.nextEvent(SignupRootViewModel.Step.GENDER) }
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

        val TAG = GenderFragment::class.simpleName.toString()

        fun newInstance() = GenderFragment()
    }
}
