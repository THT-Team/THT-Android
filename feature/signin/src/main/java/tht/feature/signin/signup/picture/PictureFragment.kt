package tht.feature.signin.signup.picture

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.extension.repeatOnStarted
import tht.feature.signin.databinding.FragmentPictureBinding
import tht.feature.signin.signup.SignupRootBaseFragment
import tht.feature.signin.signup.SignupRootViewModel

@AndroidEntryPoint
class PictureFragment : SignupRootBaseFragment<PictureViewModel, FragmentPictureBinding>(FragmentPictureBinding::inflate) {

    override val viewModel by viewModels<PictureViewModel>()

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.PICTURE)
    }

    override fun setListener() {
        binding.btnNext.setOnClickListener { rootViewModel.nextEvent(SignupRootViewModel.Step.PICTURE) }
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

        val TAG = PictureFragment::class.simpleName.toString()

        fun newInstance() = PictureFragment()
    }
}
