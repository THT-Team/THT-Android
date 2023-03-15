package tht.feature.signin.signup

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tht.core.ui.delegate.viewBinding
import tht.core.ui.extension.repeatOnStarted
import tht.feature.signin.R
import tht.feature.signin.databinding.ActivitySignupRootBinding

@AndroidEntryPoint
class SignupRootActivity : AppCompatActivity() {

    private val viewModel: SignupRootViewModel by viewModels()
    private val binding: ActivitySignupRootBinding by viewBinding(ActivitySignupRootBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListener()
        observeData()
    }

    private fun setListener() {
        binding.ivBack.setOnClickListener {
            viewModel.backEvent()
        }
    }

    private fun observeData() {
        repeatOnStarted {
            launch {
                viewModel.sideEffectFlow.collect {
                    when (it) {
                        is SignupRootViewModel.SignupRootSideEffect.Back -> {
                            val navController = findNavController(R.id.fc_nav_host)
                            if (navController.currentDestination?.id == R.id.nicknameFragment) {
                                onBackPressed()
                            } else {
                                navController.popBackStack()
                            }
                        }
                        is SignupRootViewModel.SignupRootSideEffect.NavigateNextView -> {
                            val navController = findNavController(R.id.fc_nav_host)
                            when (it.step) {
                                SignupRootViewModel.Step.EMPTY -> {}
                                SignupRootViewModel.Step.NICKNAME -> {
                                    navController.navigate(R.id.action_nicknameFragment_to_birthdayFragment)
                                }
                                SignupRootViewModel.Step.BIRTHDAY -> {
                                    navController.navigate(R.id.action_birthdayFragment_to_genderFragment)
                                }
                                SignupRootViewModel.Step.GENDER -> {
                                    navController.navigate(R.id.action_genderFragment_to_pictureFragment)
                                }
                                SignupRootViewModel.Step.PICTURE -> {
                                    navController.navigate(R.id.action_pictureFragment_to_interestFragment)
                                }
                                SignupRootViewModel.Step.INTEREST -> {
                                    navController.navigate(R.id.action_interestFragment_to_idealTypeFragment)
                                }
                                SignupRootViewModel.Step.IDEAL_TYPE -> {
                                    navController.navigate(R.id.action_idealTypeFragment_to_introductionFragment)
                                }
                                SignupRootViewModel.Step.INTRODUCTION -> {
                                    navController.navigate(R.id.action_introductionFragment_to_locationFragment)
                                }
                                SignupRootViewModel.Step.LOCATION -> {}
                            }
                        }
                    }
                }
            }

            launch {
                viewModel.uiStateFlow.collect {
                    when (it) {
                        is SignupRootViewModel.SignupRootUiState.Progress -> {
                            binding.pbSignup.setProgress(it.step.ordinal, true)
                        }
                    }
                }
            }
        }
    }
}
