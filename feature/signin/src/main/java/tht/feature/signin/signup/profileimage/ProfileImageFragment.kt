package tht.feature.signin.signup.profileimage

import android.os.Bundle
import android.text.InputFilter
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.feature.signin.databinding.FragmentProfileImageBinding
import tht.feature.signin.signup.SignupRootBaseFragment
import tht.feature.signin.signup.SignupRootViewModel
import tht.feature.signin.signup.nickname.NicknameViewModel
import tht.feature.signin.util.StringUtil

@AndroidEntryPoint
class ProfileImageFragment : SignupRootBaseFragment<ProfileImageViewModel, FragmentProfileImageBinding>() {

    override val binding: FragmentProfileImageBinding by viewBinding(FragmentProfileImageBinding::inflate)

    override val viewModel by viewModels<ProfileImageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.PROFILE_IMAGE)
    }

    override fun setListener() {
        binding.btnNext.setOnClickListener { rootViewModel.nextEvent(SignupRootViewModel.Step.PROFILE_IMAGE) }
    }

    private fun initView() {
        StringUtil.setWhiteTextColor(binding.tvProfileImageTitle, 0 until 2)
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
