package tht.feature.signin.signup.location

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.feature.signin.databinding.FragmentLocationBinding
import tht.feature.signin.signup.SignupRootBaseFragment
import tht.feature.signin.signup.SignupRootViewModel

@AndroidEntryPoint
class LocationFragment : SignupRootBaseFragment<LocationViewModel, FragmentLocationBinding>() {

    override val binding: FragmentLocationBinding by viewBinding(FragmentLocationBinding::inflate)

    override val viewModel by viewModels<LocationViewModel>()

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.LOCATION)
    }

    override fun setListener() {
        binding.btnNext.setOnClickListener { rootViewModel.nextEvent(SignupRootViewModel.Step.LOCATION) }
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

        val TAG = LocationFragment::class.simpleName.toString()

        fun newInstance() = LocationFragment()
    }
}
