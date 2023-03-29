package tht.feature.signin.signup.preferredgender

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.feature.signin.databinding.FragmentPreferredGenderBinding
import tht.feature.signin.signup.SignupRootBaseFragment
import tht.feature.signin.signup.SignupRootViewModel

@AndroidEntryPoint
class PreferredGenderFragment : SignupRootBaseFragment<PreferredGenderViewModel, FragmentPreferredGenderBinding>() {

    override val binding: FragmentPreferredGenderBinding by viewBinding(FragmentPreferredGenderBinding::inflate)

    override val viewModel by viewModels<PreferredGenderViewModel>()

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

        val TAG = PreferredGenderFragment::class.simpleName.toString()

        fun newInstance() = PreferredGenderFragment()
    }
}
