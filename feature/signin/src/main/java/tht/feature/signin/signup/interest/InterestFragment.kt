package tht.feature.signin.signup.interest

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.feature.signin.databinding.FragmentInterestBinding
import tht.feature.signin.signup.SignupRootBaseFragment
import tht.feature.signin.signup.SignupRootViewModel

@AndroidEntryPoint
class InterestFragment : SignupRootBaseFragment<InterestViewModel, FragmentInterestBinding>() {

    override val binding: FragmentInterestBinding by viewBinding(FragmentInterestBinding::inflate)

    override val viewModel by viewModels<InterestViewModel>()

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.INTEREST)
    }

    override fun setListener() {
        binding.btnNext.setOnClickListener { rootViewModel.nextEvent(SignupRootViewModel.Step.INTEREST) }
    }

    override fun initView() {
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

        val TAG = InterestFragment::class.simpleName.toString()

        fun newInstance() = InterestFragment()
    }
}
