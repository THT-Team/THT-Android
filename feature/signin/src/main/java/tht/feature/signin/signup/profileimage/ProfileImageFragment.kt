package tht.feature.signin.signup.profileimage

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.extension.repeatOnStarted
import tht.feature.signin.databinding.FragmentProfileImageBinding
import tht.feature.signin.signup.SignupRootBaseFragment
import tht.feature.signin.signup.SignupRootViewModel

@AndroidEntryPoint
class ProfileImageFragment : SignupRootBaseFragment<ProfileImageViewModel, FragmentProfileImageBinding>(FragmentProfileImageBinding::inflate) {

    override val viewModel by viewModels<ProfileImageViewModel>()

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.PROFILE_IMAGE)
    }

    override fun setListener() {
        binding.btnNext.setOnClickListener { rootViewModel.nextEvent(SignupRootViewModel.Step.PROFILE_IMAGE) }
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

        val TAG = ProfileImageFragment::class.simpleName.toString()

        fun newInstance() = ProfileImageFragment()
    }
}
